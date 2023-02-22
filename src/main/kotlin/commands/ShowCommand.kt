package commands

import iostreamers.EventMessage
import iostreamers.TextColor

class ShowCommand(
    private val listOfOrganization: List<String>,
) : Command {
    override val info: String
        get() = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        if (listOfOrganization.isEmpty()) {
            return CommandResult(
                true,
                message = EventMessage.message("Коллекция пуста", TextColor.BLUE)
            )
        }

        var output = "Элементы коллекции:\n"
        output += "Элементы коллекции:\n"
        listOfOrganization.forEach {
            output += "------------------------\n"
            output += it
            output += "------------------------\n"
        }

        return CommandResult(
            true,
            message = output
        )
    }
}