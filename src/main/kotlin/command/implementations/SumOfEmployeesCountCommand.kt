package command.implementations

import Organization
import collection.CollectionWrapper
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.Messenger
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
            Messenger.message("Коллекция пуста", TextColor.BLUE)
        else
            "Общее количество работников во всех организациях: " + Messenger.message("$sum", TextColor.BLUE)

        return CommandResult(true, message = output)
    }
}