package command.implementations

import IdManager
import command.ArgumentType
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.AddIfMaxRequest

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

        args.checkArguments(argumentTypes)

        return CommandResult(
            true,
            AddIfMaxRequest(args.organizations[0], idManager),
            message = EventMessage.message("Запрос на добавление элемента отправлен", TextColor.BLUE)
        )

    }
}