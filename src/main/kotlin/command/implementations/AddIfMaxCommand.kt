package command.implementations

import IdManager
import command.*
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.AddIfMaxRequest

class AddIfMaxCommand(
    private val idManager: IdManager?,
) : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции"

    override val argumentValidator: ArgumentValidator = ArgumentValidator(listOf(ArgumentType.ORGANIZATION))

    override fun execute(args: CommandArgument): CommandResult {
        if (idManager == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")

        argumentValidator.check(args)

        return CommandResult(
            true,
            AddIfMaxRequest(args.organizations[0], idManager),
            message = EventMessage.message("Запрос на добавление элемента отправлен", TextColor.BLUE)
        )

    }
}