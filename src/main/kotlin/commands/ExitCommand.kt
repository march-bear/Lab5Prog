package commands

import exceptions.ExitCommandCall

class ExitCommand : Command {
    override val info: String
        get() = "завершить программу (без сохранения в файл)"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        throw ExitCommandCall()
    }
}