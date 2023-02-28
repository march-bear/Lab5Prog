package command.implementations

import command.ArgumentType
import command.Command
import command.CommandArgument
import command.CommandResult
import requests.AddIfMaxRequest

/**
 * Класс команды add_if_max для считывания элемента с входного потока и добавления его в коллекцию, если он больше
 * максимального элемента этой коллекции
 */
class AddIfMaxCommand : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции"

    override val argumentTypes: List<ArgumentType>
        get() = listOf(ArgumentType.ORGANIZATION)

    override fun execute(args: CommandArgument): CommandResult {
        return CommandResult(
            true,
            AddIfMaxRequest(args.organizations[0]),
            message = "Запрос на добавление элемента отправлен"
        )

    }
}