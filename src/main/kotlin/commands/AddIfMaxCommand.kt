package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import java.util.LinkedList

/**
 * Класс команды add_if_max для считывания элемента с входного потока и добавления его в коллекцию, если он больше
 * максимального элемента этой коллекции
 */
class AddIfMaxCommand(private val collection: LinkedList<Organization>, private val reader: Reader) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Команда не принимает аргументы")

        val newElement = reader.readElementForCollection(collection)
        if (newElement >= collection.max()) {
            newElement.setId(Generator.generateUniqueId(collection))
            collection.add(newElement)
            EventMessage.messageln("Элемент добавлен в коллекцию", TextColor.BLUE)
        } else
            EventMessage.messageln("Элемент не является максимальным", TextColor.BLUE)
        println()
    }

    override fun getInfo(): String =
        "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции"
}