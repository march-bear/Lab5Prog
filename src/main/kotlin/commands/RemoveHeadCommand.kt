package commands

import iostreamers.EventMessage
import Organization
import TextColor
import exceptions.InvalidArgumentsForCommandException
import java.util.LinkedList

class RemoveHeadCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Команда не принимает аргументы")

        if (collection.isEmpty()) {
            EventMessage.messageln("Команда не может быть выполнена - коллекция пуста", TextColor.RED)
            return
        }

        EventMessage.messageln("${collection.first}")
        collection.removeFirst()
        EventMessage.messageln("Элемент удален\n", TextColor.BLUE)
    }

    override fun getInfo(): String = "вывести первый элемент коллекции и удалить его"
}