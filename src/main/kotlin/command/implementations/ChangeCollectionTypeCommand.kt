package command.implementations

import collection.*
import command.*
import iostreamers.Messenger
import iostreamers.TextColor
import requests.ChangeCollectionTypeRequest

class ChangeCollectionTypeCommand: Command {
    override val info: String
        get() = "изменить тип коллекции (QUEUE/SET/LIST)"

    override val argumentValidator: ArgumentValidator = ArgumentValidator(listOf(ArgumentType.STRING))

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        val request = when (args.primitiveTypeArguments!![0].lowercase()) {
            "queue" -> ChangeCollectionTypeRequest(CollectionType.QUEUE)
            "list" -> ChangeCollectionTypeRequest(CollectionType.LIST)
            "set" -> ChangeCollectionTypeRequest(CollectionType.SET)
            else -> return CommandResult(
                false,
                message = Messenger.message("Заданный тип коллекции не найден", TextColor.RED),
            )
        }

        return CommandResult(
            true,
            request,
            message = Messenger.message("Запрос на изменение типа коллекции отправлен", TextColor.BLUE)
        )
    }
}