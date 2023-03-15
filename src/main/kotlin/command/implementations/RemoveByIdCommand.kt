package command.implementations

import Organization
import command.*
import requests.RemoveByIdRequest

class RemoveByIdCommand : Command {
    override val info: String
        get() = "удалить элемент из коллекции по его id (id указывается после имени команды)"
    override val argumentValidator = ArgumentValidator(listOf(ArgumentType.LONG))

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        val id: Long = args.primitiveTypeArguments?.get(0)?.toLong() ?: -1
        println(1)
        if (Organization.idIsValid(id))
            return CommandResult(
                true,
                RemoveByIdRequest(id),
                message = "Запрос на удаление элемента отправлен"
            )
        println(2)
        return CommandResult(false, message = "Введенное значение не является id")
    }
}