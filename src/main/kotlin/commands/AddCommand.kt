package commands

import Address
import Coordinates
import iostreamers.EventMessage
import Organization
import OrganizationType
import exceptions.InvalidArgumentsForCommandException
import exceptions.InvalidFieldValueException
import iostreamers.Reader
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.util.*

/**
 * Класс команды add для считывания элемента с входного потока и добавления его в коллекцию
 */
class AddCommand(private val collection: LinkedList<Organization>, private val reader: Reader) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Аргументы передаются на следующих строках")

        val newElement = reader.readElementForCollection(collection)
        newElement.setId(Generator.generateUniqueId(collection))
        collection.add(newElement)
    }


    override fun getInfo(): String =
        "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"
}