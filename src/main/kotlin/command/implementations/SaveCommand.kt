package command.implementations

import CollectionController
import command.*
import iostreamers.Messenger
import iostreamers.TextColor

class SaveCommand(private val controller: CollectionController?) : Command {
    override val info: String
        get() = "сохранить коллекцию в файл"

    override fun execute(args: CommandArgument): CommandResult {
        if (controller == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")

        argumentValidator.check(args)

        try {
            controller.dataFileManager.saveData()
        } catch (ex: Exception) {
            return CommandResult(
                false,
                message = Messenger.message(
                    "Ошибка во время сохранения коллекции. Сообщение ошибки: $ex",
                    TextColor.RED,
                )
            )
        }

        return CommandResult(
            true,
            message = Messenger.message("Коллекция сохранена", TextColor.BLUE)
        )
    }
}