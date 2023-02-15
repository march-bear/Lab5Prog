package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.LinkedList

/**
 * Класс команды sum_of_employees_count для вывода суммы значений полей employeesCount всех элементов коллекции
 */
class SumOfEmployeesCountCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Команда не принимает аргументы")

        if (collection.isEmpty()) {
            EventMessage.messageln("Коллекция пуста", TextColor.BLUE)
            return
        }

        var sum: Long = 0
        for (elem in collection)
            sum += elem.getEmployeesCount() ?: 0

        EventMessage.messageln("Общее количество работников во всех организациях: $sum\n", TextColor.BLUE)
    }

    override fun getInfo(): String =
        "вывести сумму значений поля employeesCount для всех элементов коллекции"
}