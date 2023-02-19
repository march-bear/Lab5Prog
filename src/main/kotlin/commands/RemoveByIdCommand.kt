package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.TextColor
import kotlinx.serialization.descriptors.PrimitiveKind
import requests.RemoveByIdRequest
import java.lang.NumberFormatException
import java.util.LinkedList
import java.util.regex.Pattern


class RemoveByIdCommand : Command {
    override val info: String
        get() = "удалить элемент из коллекции по его id (id указывается после имени команды)"
    override val argumentTypes: List<ArgumentType>
        get() = listOf(
            ArgumentType(PrimitiveKind.LONG, false, 1)
        )

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)
        val id: Long = args.args?.get(0)?.toLong() ?: -1
        if (Organization.idIsValid(id))
            return CommandResult(
                true,
                RemoveByIdRequest(id),
                message = "Запрос на удаление элемента отправлен"
            )
        return CommandResult(false, message = "Введенное значение не является id")
    }
}