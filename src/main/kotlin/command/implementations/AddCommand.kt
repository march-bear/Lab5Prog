package command.implementations

import IdManager
import command.ArgumentType
import command.Command
import command.CommandArgument
import command.CommandResult
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.AddRequest

class AddCommand(
    private val idManager: IdManager?,
) : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"

    override val argumentTypes: List<ArgumentType>
        get() = listOf(ArgumentType.ORGANIZATION)

    override fun execute(args: CommandArgument): CommandResult {
        if (idManager == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")

        return CommandResult(
            true,
            AddRequest(args.organizations[0], idManager),
            EventMessage.message("Запрос на добавление элемента в коллекцию отправлен", TextColor.BLUE)
        )
    }
}