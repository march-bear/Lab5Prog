package commands

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.LinkedList


class SaveCommand(private val collection: LinkedList<Organization>) : Command {
     override val argumentTypes: List<ArgumentType> = listOf(
        ArgumentType(PrimitiveKind.STRING, false, 0)
    )
    override val info: String
        get() = "сохранить коллекцию в файл"

    override fun execute(args: CommandArgument): CommandResult {
        args.checkArguments(argumentTypes)
        val file: OutputStreamWriter
        val fileName = args.args?.get(0)
        try {
            file = OutputStreamWriter(FileOutputStream(fileName.toString()))
        } catch (e: FileNotFoundException) {
            return CommandResult(
                false,
                message = EventMessage.message("Возникла ошибка во время открытия файла\n" +
                        "Сообщение ошибки: $e", TextColor.RED)
            )
        }

        file.write(Json.encodeToString(collection.toList()))
        file.close()

        return CommandResult(
            true,
            message = EventMessage.message("Коллекция успешно сохранена", TextColor.BLUE)
        )
    }
}