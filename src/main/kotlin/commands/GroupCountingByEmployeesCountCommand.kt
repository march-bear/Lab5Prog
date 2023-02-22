package commands

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor

/**
 * Класс команды group_counting_by_employees_count для объединения элементов в группы
 * по значению полей employeesCount и вывод количества элементов в каждой из групп
 */
class GroupCountingByEmployeesCountCommand(private val map: Map<Long?, List<Organization>>) : Command {
    override val info: String
        get() = "сгруппировать элементы коллекции по значению поля employeesCount, " +
                "вывести количество элементов в каждой группе"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        if (map.isEmpty()) {
            return CommandResult(
                true,
                message = EventMessage.message("Коллекция пуста", TextColor.BLUE)
            )
        }

        var output = ""
        map.forEach {
            output += EventMessage.message("employeesCount=${it.key}: ")
            output += EventMessage.message("${it.value.size}\n", TextColor.BLUE)
        }

        return CommandResult(
            true,
            message = output
        )
    }
}