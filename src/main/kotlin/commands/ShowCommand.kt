package commands

import iostreamers.EventMessage
import Organization
import TextColor
import exceptions.InvalidArgumentsForCommandException
import java.util.LinkedList

class ShowCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        if (args != null) {
            throw InvalidArgumentsForCommandException("Команда не принимает на вход аргументы")
        }
        if (collection.size == 0) {
            EventMessage.messageln("Коллекция пуста", TextColor.BLUE)
            return
        }
        EventMessage.messageln("Элементы коллекции:")
        for (element in collection) {
            EventMessage.messageln("----------------------")
            println("$element")
            EventMessage.messageln("----------------------")
        }
        println()
    }

    override fun getInfo(): String =
        "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"
}