package command.implementations

import Organization
import OrganizationFactory
import command.*
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.UpdateRequest

class UpdateCommand : Command {
    override val info: String
        get() = "обновить значение элемента коллекции, id которого равен заданному"
    override val argumentTypes: List<ArgumentType>
        get() = listOf(ArgumentType.LONG, ArgumentType.ORGANIZATION)

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        val id: Long = args.primitiveTypeArguments?.get(0)?.toLong() ?: -1
        if (!Organization.idIsValid(id))
            return CommandResult(false, message = "Введенное значение не является id")

        return CommandResult(
            true,
            UpdateRequest(id, args.organizations[0]),
            message = EventMessage.message(
                "Запрос на обновление значения элемента коллекции с id $id отправлен",
                TextColor.BLUE,
            )
        )
    }
}