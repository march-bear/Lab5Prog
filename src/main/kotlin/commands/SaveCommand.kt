package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.TextColor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.LinkedList
import java.util.regex.Pattern

/**
 * Класс команды save для сохранения коллекции в файл
 */
class SaveCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        val fileNames = args?.split(Pattern.compile("\\s+"))
        val file: OutputStreamWriter
        when (fileNames?.size) {
            0, null -> throw InvalidArgumentsForCommandException("Имя файла для сохранения не указано")
            1 -> {
                try {
                    file = OutputStreamWriter(FileOutputStream(fileNames[0]))
                } catch (e: FileNotFoundException) {
                    EventMessage.messageln("Возникла ошибка во время открытия файла", TextColor.RED)
                    EventMessage.messageln("Сообщение ошибки: $e", TextColor.RED)
                    return
                }
            }
            else -> throw InvalidArgumentsForCommandException("Нужно указать путь к одному файлу")
        }
            file.write(Json.encodeToString(collection.toList()))
            EventMessage.messageln("Коллекция успешно сохранена", TextColor.BLUE)
            file.close()

    }

    override fun getInfo(): String = "сохранить коллекцию в файл"
}