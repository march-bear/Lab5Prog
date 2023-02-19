package commands

import iostreamers.EventMessage
import Organization
import iostreamers.TextColor
import exceptions.InvalidArgumentsForCommandException
import requests.RemoveHeadRequest
import java.util.LinkedList

/**
 * Класс команды remove_head вывода первого элемента коллекции и его последующего удаления
 */
class RemoveHeadCommand(private val collection: LinkedList<Organization>) : Command {
    override val info: String
        get() = "вывести первый элемент коллекции и удалить его"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        return CommandResult(
            true,
            RemoveHeadRequest(),
            message = "Запрос на удаление первого элемента коллекции отправлен",
        )
    }
}