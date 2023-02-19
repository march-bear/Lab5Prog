package commands

import exceptions.ExitCommandCall

/**
 * Класс команды exit для выхода из программы
 */
class ExitCommand : Command {
    override val info: String
        get() = "завершить программу (без сохранения в файл)"

    override fun execute(args: CommandArgument): CommandResult {
        throw ExitCommandCall()
    }

    override val argumentTypes: List<ArgumentType>
        get() = TODO("Not yet implemented")
}