package command.implementations

import command.ArgumentType
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.AddRequest

class AddCommand : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"

    override val argumentTypes: List<ArgumentType>
        get() = listOf(ArgumentType.ORGANIZATION)

    override fun execute(args: CommandArgument): CommandResult {
        return CommandResult(
            true,
            AddRequest(args.organizations[0]),
            EventMessage.message("Запрос на добавление элемента в коллекцию отправлен", TextColor.BLUE)
        )
    }
}