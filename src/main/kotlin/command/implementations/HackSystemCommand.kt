package command.implementations

import command.Command
import command.CommandArgument
import command.CommandResult
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.TextColor

class HackSystemCommand : Command {
    override val info: String
        get() = "взломать систему"

    override fun execute(args: CommandArgument): CommandResult {
        try {
            args.checkArguments(argumentTypes)
        } catch (ex: InvalidArgumentsForCommandException) {
            return CommandResult(
                true, message = EventMessage.message(
                    "Не усложняйте работу команде - она прекрасно взломает систему и без доп. аргументов",
                    TextColor.RED,
                )
            )
        }
        return CommandResult(true, message = EventMessage.oops())
    }
}