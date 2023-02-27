package command.implementations

import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor


class PrintUniquePostalAddressCommand(private val setOfAddresses: Set<String>) : Command {
    override val info: String
        get() = "вывести уникальные значения поля postalAddress всех элементов в коллекции"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        if (setOfAddresses.isEmpty()) {
            return CommandResult(
                true,
                message = EventMessage.message("Коллекция пуста", TextColor.BLUE)
            )
        }

        var output = EventMessage.message("Уникальные ZIP-коды элементов:\n")
        var counter = 0
        setOfAddresses.forEach {
            counter++
            output += EventMessage.message("$it\n", TextColor.BLUE)
        }

        return CommandResult(
            true,
            message = output
        )
    }
}