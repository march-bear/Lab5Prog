package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import java.util.LinkedList

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