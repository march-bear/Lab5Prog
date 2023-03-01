package command.implementations

import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.ClearRequest

class ClearCommand : Command {
    override val info: String
        get() = "очистить коллекцию"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        return CommandResult(
            true,
            ClearRequest(),
            message = EventMessage.message("Запрос на очистку коллекции отправлен", TextColor.BLUE)
        )
    }
}