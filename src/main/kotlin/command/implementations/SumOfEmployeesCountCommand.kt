package command.implementations

import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor

class SumOfEmployeesCountCommand(
    private val sum: Long,
) : Command {
    override val info: String
        get() = "вывести сумму значений поля employeesCount для всех элементов коллекции"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        val output = if (sum == 0L)
            EventMessage.message("Коллекция пуста\n", TextColor.BLUE)
        else
            EventMessage.message("Общее количество работников во всех организациях: $sum\n", TextColor.BLUE)

        return CommandResult(true, message = output)
    }
}