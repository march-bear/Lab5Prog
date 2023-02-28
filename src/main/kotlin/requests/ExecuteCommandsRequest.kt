package requests

import CollectionController
import Organization
import command.Command
import command.CommandArgument
import exceptions.CancellationException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class ExecuteCommandsRequest(
    private val commands: LinkedList<Pair<Command, CommandArgument>>,
    private val controller: CollectionController,
) : Request {
    private val requests: Stack<Request> = Stack()
    override fun process(collection: LinkedList<Organization>): String {
        try {
            for (i in commands.indices) {
                val (command, args) = commands[i]
                val (commandCompleted, request, message) = command.execute(args)
                if (commandCompleted) {
                    if (request != null) {
                        requests.add(request)
                        request.process(collection)
                    }
                    EventMessage.printMessage(message)
                } else
                    throw Exception(message)
            }
        } catch (ex: Exception) {
            cancel()
            return EventMessage.message(
                "Ошибка во время исполнения скрипта. Сообщение ошибки:\n$ex",
                TextColor.RED)
        }

        collection.clear()
        return EventMessage.message("Скрипт выполнен", TextColor.BLUE)
    }

    override fun cancel(): String {
        try {
            while (requests.isNotEmpty())
                requests.pop().cancel()
        } catch (ex: CancellationException) {

        }
        return "Запрос на исполнение скрипта отменен"
    }
}