package command.implementations

import Organization
import collection.CollectionWrapper
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor

class SumOfEmployeesCountCommand(
    private val collection: CollectionWrapper<Organization>,
) : Command {
    override val info: String
        get() = "вывести сумму значений поля employeesCount для всех элементов коллекции"

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        val sum = collection.sumOf { it.employeesCount ?: 0 }
        val output = if (sum == 0L)
            EventMessage.message("Коллекция пуста", TextColor.BLUE)
        else
            "Общее количество работников во всех организациях: " + EventMessage.message("$sum", TextColor.BLUE)

        return CommandResult(true, message = output)
    }
}