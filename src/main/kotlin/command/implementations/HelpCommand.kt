package command.implementations

import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor

class HelpCommand (private val commandMap: Map<String, String>) : Command {
    override val info: String
        get() = "вывести справку по доступным командам"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        var output = ""

        commandMap.forEach {
            output += EventMessage.message(String.format("%-40s", "${it.key}:"))
            output += EventMessage.message("${it.value}", TextColor.BLUE)
            output += "\n"
        }

        return CommandResult(true, message = output)
    }
}