package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import java.util.LinkedList

class GroupCountingByEmployeesCountCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Команда не принимает аргументы")

        if (collection.isEmpty()) {
            EventMessage.messageln("Коллекция пуста", TextColor.BLUE)
            return
        }

        val employeesCountGroups = HashMap<Long?, Long>()
        for (elem in collection)
            employeesCountGroups[elem.getEmployeesCount()] =
                employeesCountGroups[elem.getEmployeesCount()]?.plus(1) ?: 1

        for (elem in employeesCountGroups) {
            EventMessage.message("employeesCount=${elem.key}: ")
            EventMessage.messageln("${elem.value}", TextColor.BLUE)
        }
    }

    override fun getInfo(): String =
        "сгруппировать элементы коллекции по значению поля employeesCount, вывести количество элементов в каждой группе"
}