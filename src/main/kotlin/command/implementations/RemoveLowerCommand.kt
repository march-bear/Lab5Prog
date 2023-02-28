package command.implementations

import OrganizationFactory
import command.ArgumentType
import command.Command
import command.CommandArgument
import command.CommandResult
import requests.RemoveLowerRequest

/**
 * Класс команды remove_lower для удаления всех элементов коллекции, меньших, чем введенный
 */
class RemoveLowerCommand : Command {
    override val info: String
        get() = "удалить из коллекции все элементы, меньшие, чем заданный"

    override val argumentTypes: List<ArgumentType>
        get() = listOf(ArgumentType.ORGANIZATION)

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        return CommandResult(
            true,
            RemoveLowerRequest(args.organizations[0]),
            message = "Запрос на удаление всех элементов, меньших заданного, отправлен"
        )
    }
}