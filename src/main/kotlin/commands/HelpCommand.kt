package commands

import EventMessage
import kotlin.collections.HashMap
import kotlin.math.floor

class HelpCommand (private val commandMap: HashMap<String, Command>) : Command {
    override fun execute(s: String) {
        for (command in commandMap) {
            EventMessage.defaultMessage("${command.key}:" + "\t".repeat(10 - floor((command.key.length + 1) / 4.0).toInt()))
            EventMessage.blueMessageln(command.value.getInfo())
        }
        println()
    }

    override fun getInfo(): String = "вывести справку по доступным командам"
}