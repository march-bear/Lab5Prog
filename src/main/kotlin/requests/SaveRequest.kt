package requests

import Organization
import collection.CollectionWrapper
import iostreamers.EventMessage
import iostreamers.TextColor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStreamWriter

/**
 * Запрос на сохранение коллекции в файл
 */
class SaveRequest(private val fileToSave: String) : Request {
    override fun process(collection: CollectionWrapper<Organization>): String {
        val file: OutputStreamWriter
        try {
            file = OutputStreamWriter(FileOutputStream(fileToSave))
        } catch (e: FileNotFoundException) {
            return EventMessage.message("Возникла ошибка во время открытия файла\n" +
                        "Сообщение ошибки: $e", TextColor.RED)
        }

        file.write(Json.encodeToString(collection.toList()))
        file.close()

        return EventMessage.message("Коллекция успешно сохранена в файл $fileToSave", TextColor.BLUE)
    }

    override fun cancel(): String {
        return "Запрос на сохранение коллекции не может быть отменен"
    }

}