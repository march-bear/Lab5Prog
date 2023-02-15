package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import java.util.LinkedList

/**
 * Класс команды remove_lower для удаления всех элементов коллекции, меньших, чем введенный
 */
class RemoveLowerCommand(private val collection: LinkedList<Organization>, private val reader: Reader) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Аргументы вводятся на следующих строках")

        val testElement = reader.readElementForCollection(LinkedList())

        var counter = 0
        while (counter < collection.size) {
            if (collection[counter] < testElement) {
                val elemId = collection[counter].getId()
                collection.removeAt(counter)
                EventMessage.messageln("Удален элемент с id $elemId", TextColor.BLUE)
                continue
            }
            counter++
        }
    }

    override fun getInfo(): String = "удалить из коллекции все элементы, меньшие, чем заданный"
}