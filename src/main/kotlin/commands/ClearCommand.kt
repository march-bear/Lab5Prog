package commands

import requests.ClearRequest

class ClearCommand : Command {
    override val argumentTypes: List<ArgumentType>
        get() = listOf()
    override val info: String
        get() = "очистить коллекцию"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)
        return CommandResult(
            true,
            ClearRequest(),
            message = "Запрос на очистку коллекции отправлен..."
        )
    }
}