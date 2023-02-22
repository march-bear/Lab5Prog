package commands

/**
 * Интерфейс, реализуемый всеми классами команд
 */
interface Command {
    /**
     * Строка с информацией о команде
     */
    val info: String

    /**
     * Список типов аргументов (объектов data-класса ArgumentType)
     */
    val argumentTypes: List<ArgumentType>
        get() = listOf()

    /**
     * Исполняет команду
     * @param args аргументы, поданные с командой, представленные строкой
     */
    fun execute(args: CommandArgument): CommandResult //todo update args logic + create validate logic + return command execution info
}