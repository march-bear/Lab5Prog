package commands

import EventMessage
import Organization
import java.util.LinkedList


class InfoCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(s: String) {
        EventMessage.defaultMessageln("Информация о коллекции:")

        EventMessage.defaultMessageln("-------------------------")

        EventMessage.defaultMessage("Тип коллекции: ")
        EventMessage.blueMessageln("LinkedList")

        EventMessage.defaultMessage("Дата инициализации: ")
        EventMessage.blueMessageln("неизвестна")

        EventMessage.defaultMessage("Количество элементов: ")
        EventMessage.blueMessageln(collection.size.toString())

        EventMessage.defaultMessage("id максимального элемента: ")
        EventMessage.blueMessageln(collection.size.toString())

        EventMessage.defaultMessage("id минимального элемента: ")
        EventMessage.blueMessageln(collection.size.toString())
        EventMessage.defaultMessageln("-------------------------")

        EventMessage.defaultMessageln("\n\u00a9 ООО \"Мартовский Мишка\". Все права защищены\n")
    }

    override fun getInfo(): String =
        "вывести в стандартный поток вывода информацию о коллекции"
}