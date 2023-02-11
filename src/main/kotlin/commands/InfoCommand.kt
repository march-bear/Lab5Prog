package commands

import EventMessage
import Organization
import java.util.LinkedList


class InfoCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(s: String) {
        EventMessage.defaultMessageln("Информация о коллекции:")

        EventMessage.blueMessage("Количество элементов: ")
        EventMessage.defaultMessage(collection.size.toString())

        EventMessage.blueMessage("Количество элементов: ")
        EventMessage.defaultMessage(collection.size.toString())

        EventMessage.blueMessage("Количество элементов: ")
        EventMessage.defaultMessage(collection.size.toString())
    }

    override fun getInfo(): String =
        "вывести в стандартный поток вывода информацию о коллекции"
}