package command.implementations

import Organization
import command.*
import requests.RemoveByIdRequest

class RemoveByIdCommand : Command {
    override val info: String
        get() = "удалить элемент из коллекции по его id (id указывается после имени команды)"
    override val argumentTypes: List<ArgumentType>
        get() = listOf(ArgumentType.LONG)

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)
        val id: Long = args.primitiveTypeArguments?.get(0)?.toLong() ?: -1
        if (Organization.idIsValid(id))
            return CommandResult(
                true,
                RemoveByIdRequest(id),
                message = "Запрос на удаление элемента отправлен"
            )
        return CommandResult(false, message = "Введенное значение не является id")
    }
}