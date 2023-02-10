package commands

import kotlin.collections.HashMap

class HelpCommand (private val commandMap: HashMap<String, Command>) : Command {
    override fun execute() {
        for (command in commandMap) {
            EventMessage.defaultMessage("${command.key}: ")
            EventMessage.blueMessageln(command.value.getInfo())
        }
    }

    override fun getInfo(): String = "вывести справку по доступным командам"
}