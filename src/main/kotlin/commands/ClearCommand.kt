package commands

import iostreamers.EventMessage
import Organization
import iostreamers.TextColor
import java.util.LinkedList

/**
 * Класс команды clear для удаления всех элементов из коллекции
 */
class ClearCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        collection.clear()
        EventMessage.messageln("Коллекция очищена", TextColor.BLUE)
    }

    override fun getInfo(): String = "очистить коллекцию"
}