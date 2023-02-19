package commands

import iostreamers.EventMessage
import exceptions.InvalidArgumentsForCommandException
import iostreamers.TextColor

/**
 * Класс команды help для вывода информации о всех зарегистрированных командах
 */
class HelpCommand (private val commandMap: Map<String, String>) : Command {
    override val info: String
        get() = "вывести справку по доступным командам"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        var output = ""

        commandMap.forEach {
            output += String.format("%-40s", "${it.key}:")
            output += EventMessage.message("$it.value\n", TextColor.BLUE)
        }

        return CommandResult(true, message = output)
    }
}