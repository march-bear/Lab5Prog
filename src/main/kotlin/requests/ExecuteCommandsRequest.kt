package requests

import Organization
import command.Command
import command.CommandArgument
import exceptions.CancellationException
import exceptions.CommandIsNotCompletedException
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

/**
 * Запрос на исполнение команд
 * @param commands список команд на исполнение,
 * хранящий пары "Объект команды": "Аргументы команды"
 */
class ExecuteCommandsRequest(
    private val commands: LinkedList<Pair<Command, CommandArgument>>,
) : Request {
    private val requests: Stack<Request> = Stack()
    private var collection: LinkedList<Organization>? = null
    override fun process(collection: LinkedList<Organization>): String {
        this.collection = collection
        try {
            for (i in commands.indices) {
                val (command, args) = commands[i]
                val (commandCompleted, request, message) = command.execute(args)
                if (commandCompleted) {
                    if (request != null) {
                        request.process(collection)
                        requests.add(request)
                    }
                    if (message != null) EventMessage.printMessage(message)
                } else
                    throw CommandIsNotCompletedException("Команда не была выполнена. Сообщение о выполнении:\n" +
                            "$message")
            }
        } catch (ex: CommandIsNotCompletedException) {
            cancel()
            return EventMessage.message(
                "Ошибка во время исполнения скрипта. Сообщение ошибки:\n$ex",
                TextColor.RED)
        } catch (ex: InvalidArgumentsForCommandException) {
            cancel()
            return EventMessage.message(
                "Ошибка во время исполнения скрипта. Сообщение ошибки:\n$ex",
                TextColor.RED)
        }

        return EventMessage.message("Скрипт выполнен", TextColor.BLUE)
    }

    override fun cancel(): String {
        if (collection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")

        val canceledRequests: Stack<Request> = Stack()
        try {
            while (requests.isNotEmpty()) {
                canceledRequests.add(requests.pop())
                canceledRequests.peek().cancel()
            }
        } catch (ex: CancellationException) {
            canceledRequests.pop()
            while(canceledRequests.isNotEmpty())
                canceledRequests.pop().process(collection!!)
            throw CancellationException("Отмена запроса невозможна. Коллекция уже была модифицирована")
        }

        collection = null
        requests.clear()
        return "Запрос на исполнение скрипта отменен"
    }
}