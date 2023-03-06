package command.implementations

import Organization
import collection.CollectionWrapper
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor

class PrintUniquePostalAddressCommand(private val collection: CollectionWrapper<Organization>) : Command {
    override val info: String
        get() = "вывести уникальные значения поля postalAddress всех элементов в коллекции"

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        val setOfAddresses = collection.map { it.postalAddress.toString() }.toSet()
        if (setOfAddresses.isEmpty()) {
            return CommandResult(
                true,
                message = EventMessage.message("Коллекция пуста", TextColor.BLUE)
            )
        }

        var output = EventMessage.message("Уникальные ZIP-коды элементов:")

        setOfAddresses.forEach {
            output += EventMessage.message("\n$it", TextColor.BLUE)
        }

        return CommandResult(
            true,
            message = output
        )
    }
}