package commands

/**
 * Интерфейс, имплементируемый всеми командами
 */
interface Command {
    /**
     * Исполняет команду
     * @param args аргументы, поданные с командой
     */
    fun execute(args: String?)
    /**
     * Возвращает строку с информацией о команде
     */
    fun getInfo(): String
}