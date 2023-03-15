package command.implementations

import IdManager
import Organization
import command.*
import requests.RemoveByIdRequest

class RemoveByIdCommand(private val idManager: IdManager?) : Command {
    override val info: String
        get() = "удалить элемент из коллекции по его id (id указывается после имени команды)"
    override val argumentValidator = ArgumentValidator(listOf(ArgumentType.LONG))

    override fun execute(args: CommandArgument): CommandResult {
        if (idManager == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")

        argumentValidator.check(args)

        val id: Long = args.primitiveTypeArguments?.get(0)?.toLong() ?: -1
        if (Organization.idIsValid(id))
            return CommandResult(
                true,
                RemoveByIdRequest(id, idManager),
                message = "Запрос на удаление элемента отправлен"
            )

        return CommandResult(false, message = "Введенное значение не является id")
    }
}