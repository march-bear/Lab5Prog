package command.implementations

import command.Command
import command.CommandArgument
import command.CommandResult
import requests.RemoveHeadRequest

/**
 * Класс команды remove_head вывода первого элемента коллекции и его последующего удаления
 */
class RemoveHeadCommand : Command {
    override val info: String
        get() = "вывести первый элемент коллекции и удалить его"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)

        return CommandResult(
            true,
            RemoveHeadRequest(),
            message = "Запрос на удаление первого элемента коллекции отправлен",
        )
    }
}