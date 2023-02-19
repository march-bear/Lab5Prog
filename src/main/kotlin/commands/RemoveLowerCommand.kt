package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import requests.RemoveLowerRequest
import java.util.LinkedList

/**
 * Класс команды remove_lower для удаления всех элементов коллекции, меньших, чем введенный
 */
class RemoveLowerCommand(private val collection: LinkedList<Organization>, private val reader: Reader) : Command {
    override val info: String
        get() = "удалить из коллекции все элементы, меньшие, чем заданный"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        val testElement = reader.readElementForCollection(LinkedList())

        return CommandResult(
            true,
            RemoveLowerRequest(testElement),
            message = "Запрос на удаление всех элементов, меньших заданного, отправлен"
        )
    }
}