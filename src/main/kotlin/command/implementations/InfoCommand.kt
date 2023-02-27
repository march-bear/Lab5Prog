package command.implementations

import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*


class InfoCommand(
    private val collectionSize: Int,
    private val maxElementId: Long?,
    private val minElementId: Long?,
    private val initializationDate: Date?,
) : Command {
    override val info: String
        get() = "вывести в стандартный поток вывода информацию о коллекции"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        var output = ""

        output += EventMessage.message("Информация о коллекции:\n")

        output += EventMessage.message("-------------------------\n")

        output += EventMessage.message("Тип коллекции: ")
        output += EventMessage.message("LinkedList\n", TextColor.BLUE)

        output += EventMessage.message("Дата инициализации: ")
        output += EventMessage.message("${initializationDate ?: "<not found>"}\n", TextColor.BLUE)

        output += EventMessage.message("Количество элементов: ")
        output += EventMessage.message("$collectionSize\n", TextColor.BLUE)

        output += EventMessage.message("id максимального элемента: ")
        output += EventMessage.message("${maxElementId ?: "<not found>"}\n", TextColor.BLUE)

        output += EventMessage.message("id минимального элемента: ")
        output += EventMessage.message("${minElementId ?: "<not found>"}\n", TextColor.BLUE)
        output += EventMessage.message("-------------------------\n")

        output += EventMessage.message("\n\u00a9 ООО \"Мартовский Мишка\". Все права защищены\n")

        return CommandResult(true, message = output)
    }
}