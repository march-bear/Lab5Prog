package commands

import OrganizationFactory
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.AddRequest

class AddCommand(
    private val factory: OrganizationFactory,
) : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes, "Поля нового элемента передаются на следующих строках")

        val newElement = factory.newOrganization()
        return CommandResult(
            true,
            AddRequest(newElement),
            EventMessage.message("Запрос на добавление элемента в коллекцию отправлен", TextColor.BLUE)
        )
    }
}