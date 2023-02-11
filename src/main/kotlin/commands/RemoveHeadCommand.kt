package commands

import EventMessage
import Organization
import java.util.LinkedList

class RemoveHeadCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(s: String) {
        if (collection.isEmpty()) {
            EventMessage.redMessageln("Команда не может быть выполнена - коллекция пуста")
            return
        }
        EventMessage.defaultMessageln("${collection.first}")
        collection.remove()
    }

    override fun getInfo(): String = "вывести первый элемент коллекции и удалить его"
}