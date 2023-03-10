package command.implementations

import command.*
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.SaveRequest

class SaveCommand : Command {
    override val info: String
        get() = "сохранить коллекцию в файл"

    override val argumentValidator = ArgumentValidator(listOf(ArgumentType.STRING))

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        return CommandResult(
            true,
            SaveRequest(args.primitiveTypeArguments!![0]),
            message = EventMessage.message("Запрос на сохранение коллекции отправлен", TextColor.BLUE)
        )
    }
}