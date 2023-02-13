package commands

import iostreamers.EventMessage
import Organization
import TextColor
import java.util.LinkedList

class ClearCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(s: String?) {
        collection.clear()
        EventMessage.messageln("Коллекция очищена", TextColor.BLUE)
    }

    override fun getInfo(): String = "очистить коллекцию"
}