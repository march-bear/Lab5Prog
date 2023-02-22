package commands

import Organization
import OrganizationFactory
import iostreamers.EventMessage
import iostreamers.TextColor
import kotlinx.serialization.descriptors.PrimitiveKind
import requests.UpdateRequest

class UpdateCommand(private val factory: OrganizationFactory) : Command {
    override val info: String
        get() = "обновить значение элемента коллекции, id которого равен заданному"
    override val argumentTypes: List<ArgumentType>
        get() = listOf(
            ArgumentType(PrimitiveKind.LONG, false, 1)
        )

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        val id: Long = args.args?.get(0)?.toLong() ?: -1

        if (!Organization.idIsValid(id))
            return CommandResult(false, message = "Введенное значение не является id")

        val newValueOfElement = factory.newOrganization()

        return CommandResult(
            true,
            UpdateRequest(id, newValueOfElement),
            message = EventMessage.message(
                "Запрос на обновление значения элемента коллекции с id $id отправлен",
                TextColor.BLUE,
            )
        )
    }
}