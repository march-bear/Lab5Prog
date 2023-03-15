package command.implementations

import Organization
import collection.CollectionWrapper
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.Messenger
import iostreamers.TextColor

/**
 * Класс команды group_counting_by_employees_count для объединения элементов в группы
 * по значению полей employeesCount и вывод количества элементов в каждой из групп
 */
class GroupCountingByEmployeesCountCommand(private val collection: CollectionWrapper<Organization>) : Command {
    override val info: String
        get() = "сгруппировать элементы коллекции по значению поля employeesCount, " +
                "вывести количество элементов в каждой группе"

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        val map = collection.groupBy { it.employeesCount }
        if (map.isEmpty()) {
            return CommandResult(
                true,
                message = Messenger.message("Коллекция пуста", TextColor.BLUE)
            )
        }

        var output = ""
        map.forEach {
            output += Messenger.message("employeesCount=${it.key}: ")
            output += Messenger.message("${it.value.size}\n", TextColor.BLUE)
        }

        return CommandResult(
            true,
            message = output
        )
    }
}