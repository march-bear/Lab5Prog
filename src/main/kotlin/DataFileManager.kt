import collection.CollectionWrapper
import exceptions.MethodCallException
import iostreamers.Messenger
import iostreamers.TextColor
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class DataFileManager(private val collection: CollectionWrapper<Organization>, dataFileName: String? = null) {
    private var dataHasBeenLoaded = false
    private val defaultFileIsUsed = dataFileName == null
    private val dataFile: File = File(if (defaultFileIsUsed) DEFAULT_FILE_NAME else dataFileName!!)

    private fun readFile(): String {
        val stream = InputStreamReader(FileInputStream(dataFile))
        val data = stream.readText()
        stream.close()
        return data
    }

    private fun writeToFile(data: String) {
        val stream = OutputStreamWriter(FileOutputStream(dataFile))
        stream.write(data)
        stream.close()
    }

    fun loadData(): String {
        if (dataHasBeenLoaded)
            throw MethodCallException("В загрузке отказано: коллекция уже была загружена")

        var output = ""
        if (defaultFileIsUsed)
            output += Messenger.message(
                "ВНИМАНИЕ! На вход не подан не один файл! Загрузка будет произведена из файла по умолчанию\n",
                TextColor.YELLOW
            )

        output += "Чтение файла с коллекцией..."
        val data = readFile()
        output += "\nЧтение завершено"

        collection.clear()
        output += "\nЗагрузка данных в коллекцию...\n"

        val tmpCollection: List<Organization>
        try {
            tmpCollection = Json.decodeFromString(data)
        } catch (e: SerializationException) {
            return output + Messenger.message(
                "\n$dataFile: загрузка прервана вследствие обнаруженной синтаксической ошибки\n",
                TextColor.RED,
            )
        } catch (e: IllegalArgumentException) {
            return output + Messenger.message(
                "$\ndataFile: загрузка прервана вследствие несоответствия формата данных в файле типу Organization\n",
                TextColor.RED,
            )
        }

        for (elem in tmpCollection) {
            output += if (elem.objectIsValid()) {
                if (CollectionController.checkUniquenessFullName(elem.fullName, collection))
                    if (CollectionController.checkUniquenessId(elem.id, collection)) {
                        collection.add(elem)
                        "\nЭлемент ${Messenger.message(elem.id.toString(), TextColor.BLUE)}: " +
                                Messenger.message("добавлен", TextColor.BLUE)
                    } else
                        Messenger.message(
                            "\nОшибка во время добавления элемента в коллекцию: id не уникален",
                            TextColor.RED
                        )
                else
                    Messenger.message(
                        "\nОшибка во время добавления элемента в коллекцию: полное имя не уникально",
                        TextColor.RED
                    )
            } else {
                Messenger.message(
                    "\nОшибка во время добавления элемента в коллекцию: элемент невалиден",
                    TextColor.RED
                )
            }
        }

        dataHasBeenLoaded = true
        return "$output\n\nЗагрузка завершена"
    }

    fun saveData(): Boolean {
        if (!dataHasBeenLoaded)
            throw MethodCallException("В сохранении отказано: коллекция ещё не была загружена")

        val data = Json.encodeToString(collection.toList())
        writeToFile(data)
        return true
    }

    companion object {
        const val DEFAULT_FILE_NAME = "data.json"
    }
}