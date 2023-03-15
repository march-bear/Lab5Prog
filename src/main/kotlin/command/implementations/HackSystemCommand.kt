package command.implementations

import command.Command
import command.CommandArgument
import command.CommandResult
import exceptions.InvalidArgumentsForCommandException
import iostreamers.Messenger
import iostreamers.TextColor

class HackSystemCommand : Command {
    override val info: String
        get() = "взломать систему"

    override fun execute(args: CommandArgument): CommandResult {
        try {
            argumentValidator.check(args)
        } catch (ex: InvalidArgumentsForCommandException) {
            return CommandResult(
                true, message = Messenger.message(
                    "Не усложняйте работу команде - она прекрасно взломает систему и без доп. аргументов ;)",
                    TextColor.BLUE,
                )
            )
        }
        return CommandResult(true, message = Messenger.oops())
    }
}