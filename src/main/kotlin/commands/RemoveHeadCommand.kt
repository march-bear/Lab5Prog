package commands

import iostreamers.EventMessage
import Organization
import TextColor
import java.util.LinkedList

class RemoveHeadCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        if (collection.isEmpty()) {
            EventMessage.messageln("Команда не может быть выполнена - коллекция пуста", TextColor.RED)
            return
        }
        EventMessage.messageln("${collection.first}")
        collection.remove()
    }

    override fun getInfo(): String = "вывести первый элемент коллекции и удалить его"
}