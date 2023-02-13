package commands

import iostreamers.EventMessage
import Organization
import exceptions.InvalidArgumentsForCommandException
import java.util.LinkedList


class InfoCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Команда не принимает аргументы")

        EventMessage.messageln("Информация о коллекции:")

        EventMessage.messageln("-------------------------")

        EventMessage.message("Тип коллекции: ")
        EventMessage.messageln("LinkedList", TextColor.BLUE)

        EventMessage.message("Дата инициализации: ")
        EventMessage.messageln("неизвестна", TextColor.BLUE)

        EventMessage.message("Количество элементов: ")
        EventMessage.messageln(collection.size.toString(), TextColor.BLUE)

        EventMessage.message("id максимального элемента: ")
        EventMessage.messageln(collection.maxOrNull()?.getId().toString(), TextColor.BLUE)

        EventMessage.message("id минимального элемента: ")
        EventMessage.messageln(collection.minOrNull()?.getId().toString(), TextColor.BLUE)
        EventMessage.messageln("-------------------------")

        EventMessage.messageln("\n\u00a9 ООО \"Мартовский Мишка\". Все права защищены\n")
    }

    override fun getInfo(): String =
        "вывести в стандартный поток вывода информацию о коллекции"
}