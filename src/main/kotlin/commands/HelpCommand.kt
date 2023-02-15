package commands

import iostreamers.EventMessage
import exceptions.InvalidArgumentsForCommandException
import iostreamers.TextColor

/**
 * Класс команды help для вывода информации о всех зарегистрированных командах
 */
class HelpCommand (private val commandMap: Map<String, Command>) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Команда не принимает на вход аргументы")
        for (command in commandMap) {
            EventMessage.message(String.format("%-40s", "${command.key}:"))
            EventMessage.messageln(command.value.getInfo(), TextColor.BLUE)
        }
        println()
    }

    override fun getInfo(): String = "вывести справку по доступным командам"
}