package command.implementations

import Organization
import collection.CollectionWrapper
import command.Command
import command.CommandArgument
import command.CommandResult
import iostreamers.EventMessage
import iostreamers.TextColor

class InfoCommand(
    private val collection: CollectionWrapper<Organization>,
) : Command {
    override val info: String
        get() = "вывести в стандартный поток вывода информацию о коллекции"

    override fun execute(args: CommandArgument): CommandResult {
        argumentValidator.check(args)

        var output = EventMessage.message("Информация о коллекции:\n")

        output += EventMessage.message("-------------------------\n")

        output += EventMessage.message("Тип коллекции: ")
        output += EventMessage.message("${collection.getCollectionType()}\n", TextColor.BLUE)

        output += EventMessage.message("Дата инициализации: ")
        output += EventMessage.message("${collection.initializationDate}\n", TextColor.BLUE)

        output += EventMessage.message("Количество элементов: ")
        output += EventMessage.message("${collection.size}\n", TextColor.BLUE)

        output += EventMessage.message("id максимального элемента: ")
        output += EventMessage.message("${if (collection.isEmpty()) "<not found>" else collection.max().id}\n",
            TextColor.BLUE)

        output += EventMessage.message("id минимального элемента: ")
        output += EventMessage.message("${if (collection.isEmpty()) "<not found>" else collection.min().id}\n",
            TextColor.BLUE)
        output += EventMessage.message("-------------------------\n")

        output += EventMessage.message("\n\u00a9 ООО \"Мартовский Мишка\". Все права защищены от вас")

        return CommandResult(true, message = output)
    }
}