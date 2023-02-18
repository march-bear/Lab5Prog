package commands

/**
 * Интерфейс, имплементируемый всеми командами
 */
interface Command {
    val info: String
    /**
     * Исполняет команду
     * @param args аргументы, поданные с командой
     */
    fun execute(args: CommandArgument): CommandResult //todo update args logic + create validate logic + return command execution info
}