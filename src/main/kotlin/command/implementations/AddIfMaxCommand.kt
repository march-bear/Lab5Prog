package command.implementations

import IdManager
import command.ArgumentType
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.AddIfMaxRequest

/**
 * Класс команды add_if_max для считывания элемента с входного потока и добавления его в коллекцию, если он больше
 * максимального элемента этой коллекции
 */
class AddIfMaxCommand(
    private val idManager: IdManager?,
) : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции"

    override val argumentTypes: List<ArgumentType>
        get() = listOf(ArgumentType.ORGANIZATION)

    override fun execute(args: CommandArgument): CommandResult {
        if (idManager == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")

        return CommandResult(
            true,
            AddIfMaxRequest(args.organizations[0], idManager),
            message = EventMessage.message("Запрос на добавление элемента отправлен", TextColor.BLUE)
        )

    }
}