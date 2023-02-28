package iostreamers

import CollectionController
import Organization
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.LinkedList

class FileContentLoader(private val collection: LinkedList<Organization>) {
    private fun readContentFromFile(fileName: String): String {
        val fileStream = FileInputStream(fileName)

        val fileReader = InputStreamReader(fileStream)
        val fileContent: String = fileReader.readText()
        fileReader.close()

        return fileContent
    }

    private fun loadDataFromFile(fileName: String = "data.json"): String {
        val content: String
        var output = ""

        try {
            output += "Чтение файла $fileName...\n"
            content = readContentFromFile(fileName)
            output += "Чтение завершено\n"
        } catch (e: FileNotFoundException) {
            return output + EventMessage.message("$fileName: ошибка во время открытия файла\n" +
                    "Сообщение ошибки: $e", TextColor.RED)
        }


        try {
            output += "Загрузка данных в коллекцию...\n"
            for (elem in Json.decodeFromString<List<Organization>>(content))
                if (CollectionController.checkUniquenessFullName(elem.fullName, collection) &&
                        CollectionController.checkUniquenessId(elem.id, collection))
                    collection.add(elem)
                else
                    output += EventMessage.message("Ошибка во время добавления элемента\n", TextColor.RED)
            output += "Загрузка завершена\n"

        } catch (e: SerializationException) {

        } catch (e: IllegalArgumentException) {

        }

        return output
    }

    fun loadDataFromFiles(fileNames: Set<String>): String {
        var output = "Загрузка коллекции...\n"

        if (fileNames.isEmpty()) {
            output += EventMessage.message("ВНИМАНИЕ! На вход не подан ни один файл. " +
                    "Загрузка данных из файла по умолчанию...\n")
            output += try {
                loadDataFromFile()
            } catch (e: FileNotFoundException) {
                EventMessage.message("data.json: ошибка во время открытия файла\n" +
                        "Сообщение ошибки: $e", TextColor.RED)
            }
        }

        fileNames.forEach {
            output += try {
                loadDataFromFile(it)
            } catch (e: FileNotFoundException) {
                EventMessage.message("$it: ошибка во время открытия файла\n" +
                        "Сообщение ошибки: $e", TextColor.RED)
            }
        }

        return output
    }
}