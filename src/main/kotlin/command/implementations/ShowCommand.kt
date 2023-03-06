package command.implementations

import Organization
import collection.CollectionWrapper
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor

class ShowCommand(
    private val collection: CollectionWrapper<Organization>,
) : Command {
    override val info: String
        get() = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        if (collection.isEmpty()) {
            return CommandResult(
                true,
                message = EventMessage.message("Коллекция пуста", TextColor.BLUE)
            )
        }

        var output = "Элементы коллекции:"
        collection.forEach {
            output += "\n------------------------"
            output += "\n" + it.toString()
            output += "\n------------------------"
        }

        return CommandResult(
            true,
            message = output
        )
    }
}