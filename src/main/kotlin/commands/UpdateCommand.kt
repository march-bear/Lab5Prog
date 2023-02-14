package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import java.lang.NumberFormatException
import java.util.LinkedList
import java.util.regex.Pattern

class UpdateCommand(private val collection: LinkedList<Organization>, private val reader: Reader) : Command {
    override fun execute(args: String?) {
        val argsList = args?.trim()?.split(Pattern.compile("\\s+"), 2)
        when (argsList?.size) {
            null -> throw InvalidArgumentsForCommandException("id не указан")
            1 -> {
                try {
                    val id = argsList[0].toLong()
                    for (i in 0 until collection.size)
                        if (collection[i].getId() == id) {
                            val collectionCopy = LinkedList<Organization>()
                            collectionCopy.addAll(collection)
                            collectionCopy.remove(collection[i])
                            val newElement = reader.readElementForCollection(collection)
                            newElement.setId(id)
                            collection[i] = newElement
                            EventMessage.messageln("Элемент обновлен", TextColor.BLUE)
                            return
                        }
                    EventMessage.messageln("Элемент с id $id не найден", TextColor.RED)
                } catch (e: NumberFormatException) {
                    throw InvalidArgumentsForCommandException("Переданный аргумент не является id")
                }
            }
            2 -> throw InvalidArgumentsForCommandException("Команда принимает один аргумент - id элемента")
        }
    }

    override fun getInfo(): String = "обновить значение элемента коллекции, id которого равен заданному"
}