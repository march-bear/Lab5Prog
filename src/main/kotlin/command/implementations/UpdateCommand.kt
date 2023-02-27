package command.implementations

import Organization
import OrganizationFactory
import command.*
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.UpdateRequest

class UpdateCommand(private val factory: OrganizationFactory) : Command {
    override val info: String
        get() = "обновить значение элемента коллекции, id которого равен заданному"
    override val argumentTypes: List<ArgumentTypeData>
        get() = listOf(
            ArgumentTypeData(ArgumentType.LONG, false)
        )

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        val id: Long = args.args?.get(0)?.toLong() ?: -1

        if (!Organization.idIsValid(id))
            return CommandResult(false, message = "Введенное значение не является id")

        val newValueOfElement = factory.newOrganizationFromInput()

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