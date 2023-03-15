package command.implementations

import command.*
import commandcallgraph.RequestGraph
import iostreamers.Messenger
import iostreamers.TextColor
import requests.RollbackRequest

class RollbackCommand(private val requestGraph: RequestGraph?): Command {
    override val info: String
        get() = "вернуть коллекцию к состоянию по id запроса. Для полного отката вводится ROOT"

    override val argumentValidator: ArgumentValidator = ArgumentValidator(listOf(ArgumentType.STRING))

    override fun execute(args: CommandArgument): CommandResult {
        if (requestGraph == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")

        argumentValidator.check(args)

        return CommandResult(
            true,
            RollbackRequest(requestGraph!!, args.primitiveTypeArguments!![0]),
            Messenger.message("Запрос на откат коллекции отправлен", TextColor.BLUE)
        )
    }
}