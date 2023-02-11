package commands

import EventMessage
import Organization
import java.util.LinkedList

class ClearCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(s: String) {
        collection.clear()
        EventMessage.blueMessageln("Коллекция очищена")
    }

    override fun getInfo(): String = "очистить коллекцию"
}