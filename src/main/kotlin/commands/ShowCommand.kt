package commands

import EventMessage
import Organization
import java.util.LinkedList

class ShowCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(arguments: String) {
        if (arguments != "")
            throw Exception()
        if (collection.size == 0) {
            EventMessage.blueMessageln("Коллекция пуста")
            return
        }
        EventMessage.defaultMessageln("Элементы коллекции:")
        for (element in collection) {
            EventMessage.defaultMessageln("----------------------")
            println("$element")
            EventMessage.defaultMessageln("----------------------")
        }
        println()
    }

    override fun getInfo(): String =
        "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"
}