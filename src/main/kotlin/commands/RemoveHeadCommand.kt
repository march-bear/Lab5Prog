package commands

import iostreamers.EventMessage
import Organization
import iostreamers.TextColor
import exceptions.InvalidArgumentsForCommandException
import java.util.LinkedList

/**
 * Класс команды remove_head вывода первого элемента коллекции и его последующего удаления
 */
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