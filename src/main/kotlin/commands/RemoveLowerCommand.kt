package commands

import OrganizationFactory
import requests.RemoveLowerRequest

/**
 * Класс команды remove_lower для удаления всех элементов коллекции, меньших, чем введенный
 */
class RemoveLowerCommand(private val factory: OrganizationFactory) : Command {
    override val info: String
        get() = "удалить из коллекции все элементы, меньшие, чем заданный"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        val testElement = factory.newOrganization()

        return CommandResult(
            true,
            RemoveLowerRequest(testElement),
            message = "Запрос на удаление всех элементов, меньших заданного, отправлен"
        )
    }
}