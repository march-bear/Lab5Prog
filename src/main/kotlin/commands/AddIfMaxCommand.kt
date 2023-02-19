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
    override val info: String
        get() = "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции"

    override fun execute(args: CommandArgument): CommandResult {
        if (args != null)
            throw InvalidArgumentsForCommandException("Команда не принимает аргументы")

        val newElement = reader.readElementForCollection(collection)
        if (newElement >= collection.max()) {
            newElement.setId(Generator.generateUniqueId(collection))
            collection.add(newElement)
            EventMessage.printMessageln("Элемент добавлен в коллекцию", TextColor.BLUE)
        } else
            EventMessage.printMessageln("Элемент не является максимальным", TextColor.BLUE)
        println()
    }

    override val argumentTypes: List<ArgumentType>
        get() = TODO("Not yet implemented")
}