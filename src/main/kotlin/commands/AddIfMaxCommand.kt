package commands

import OrganizationFactory
import requests.AddIfMaxRequest

/**
 * Класс команды add_if_max для считывания элемента с входного потока и добавления его в коллекцию, если он больше
 * максимального элемента этой коллекции
 */
class AddIfMaxCommand(
    private val factory: OrganizationFactory
) : Command {
    override val info: String
        get() = "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes, "Аргументы передаются на следующих строках")

        val newElement = factory.newOrganization()

        return CommandResult(
            true,
            AddIfMaxRequest(newElement),
            message = "Запрос на добавление элемента отправлен"
        )

    }
}