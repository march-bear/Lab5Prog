package commands

import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import requests.AddRequest
import java.util.*

/**
 * Класс команды add для считывания элемента с входного потока и добавления его в коллекцию
 */
class AddCommand(
    private val reader: Reader,
) : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes, "Поля нового элемента передаются на следующих строках")

        val newElement = reader.readElementForCollection(collection) //todo add smth like Factory
        return CommandResult(
            true,
            AddRequest(newElement),
            EventMessage.message("Запрос на добавление элемента в коллекцию отправлен", TextColor.BLUE)
        )
    }
}