package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import java.lang.NumberFormatException
import java.util.LinkedList
import java.util.regex.Pattern

/**
 * Класс команды update для изменения значения элемента коллекции с указанным id
 */
class UpdateCommand(private val collection: LinkedList<Organization>, private val reader: Reader) : Command {
    override val argumentTypes: List<ArgumentType>
        get() = TODO("Not yet implemented")

    override fun execute(args: CommandArgument): CommandResult {
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
                            EventMessage.printMessageln("Элемент обновлен", TextColor.BLUE)
                            return
                        }
                    EventMessage.printMessageln("Элемент с id $id не найден", TextColor.RED)
                } catch (e: NumberFormatException) {
                    throw InvalidArgumentsForCommandException("Переданный аргумент не является id")
                }
            }
            2 -> throw InvalidArgumentsForCommandException("Команда принимает один аргумент - id элемента")
        }
    }

    override fun getInfo(): String = "обновить значение элемента коллекции, id которого равен заданному"
}