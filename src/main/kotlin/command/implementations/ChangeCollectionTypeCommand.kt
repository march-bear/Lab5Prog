package command.implementations

import Organization
import collection.*
import command.*
import requests.ChangeCollectionTypeRequest

class ChangeCollectionTypeCommand(private val collection: CollectionWrapper<Organization>): Command {
    override val info: String
        get() = "изменить тип коллекции"

    override val argumentValidator: ArgumentValidator = ArgumentValidator(listOf(ArgumentType.STRING))

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        val request = when (args.primitiveTypeArguments!![0].lowercase()) {
            "queue" -> ChangeCollectionTypeRequest(CollectionType.QUEUE)
            "list" -> ChangeCollectionTypeRequest(CollectionType.LIST)
            "set" -> ChangeCollectionTypeRequest(CollectionType.SET)
            else -> return CommandResult(false, message = "Заданный тип коллекции не найден")
        }

        return CommandResult(
            true,
            request,
            message = "Запрос на изменение типа коллекции отправлен"
        )
    }
}