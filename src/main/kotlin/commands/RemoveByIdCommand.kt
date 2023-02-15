package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.lang.NumberFormatException
import java.util.LinkedList
import java.util.regex.Pattern

/**
 * Класс команды remove_by_id для удаления элемента из коллекции по его id
 */
class RemoveByIdCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        val argsList = args?.trim()?.split(Pattern.compile("\\s+"), 2)
        when (argsList?.size) {
            null -> throw InvalidArgumentsForCommandException("id не указан")
            1 -> {
                try {
                    val id = argsList[0].toLong()
                    for (elem in collection)
                        if (elem.getId() == id) {
                            collection.remove(elem)
                            EventMessage.messageln("Элемент удален", TextColor.BLUE)
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

    override fun getInfo(): String = "удалить элемент из коллекции по его id (id указывается после имени команды)"
}