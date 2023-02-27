package command.implementations

import command.*
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.SaveRequest


class SaveCommand : Command {
     override val argumentTypes: List<ArgumentTypeData> = listOf(
        ArgumentTypeData(ArgumentType.STRING, false)
    )
    override val info: String
        get() = "сохранить коллекцию в файл"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)
        val fileName = args.args?.get(0)

        return CommandResult(
            true,
            SaveRequest(fileName ?: ""),
            message = EventMessage.message("Коллекция успешно сохранена", TextColor.BLUE)
        )
    }
}