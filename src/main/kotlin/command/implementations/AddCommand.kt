package command.implementations

import IdManager
import command.*
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.AddRequest

class AddCommand(
    private val idManager: IdManager?,
) : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"

    override val argumentValidator = ArgumentValidator(listOf(ArgumentType.ORGANIZATION))

    override fun execute(args: CommandArgument): CommandResult {
        if (idManager == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")

        argumentValidator.check(args)

        return CommandResult(
            true,
            AddRequest(args.organizations[0], idManager),
            EventMessage.message("Запрос на добавление элемента в коллекцию отправлен", TextColor.BLUE)
        )
    }
}