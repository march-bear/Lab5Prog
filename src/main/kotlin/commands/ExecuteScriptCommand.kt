package commands

import CollectionController
import iostreamers.EventMessage
import iostreamers.Reader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import java.util.regex.Pattern

class ExecuteScriptCommand(private var collection: CollectionController, private val inputStream: InputStream?) : Command {
    override fun execute(args: String?) {
        val listOfArgs = args?.split(Pattern.compile("\\s+"))
        if (listOfArgs?.size != 1) {
            EventMessage.messageln("Путь к файлу не указан", TextColor.RED)
            return
        }

        if (inputStream != System.`in`)
            throw java.lang.RuntimeException("Прервана попытка запуска скрипта во время исполнения другого скрипта")

        val script: String
        try {
            val inputStreamReader = InputStreamReader(FileInputStream(listOfArgs[0]))
            script = inputStreamReader.readText()
            inputStreamReader.close()
        } catch (e: FileNotFoundException) {
            EventMessage.messageln("${listOfArgs[0]}: файл не найден", TextColor.RED)
            return
        }

        try {
            collection.executeScript(script)
        } catch (e: RuntimeException) {
            EventMessage.messageln(e.message.toString(), TextColor.RED)
        }
    }

    override fun getInfo(): String =
        "считать и исполнить скрипт из указанного файла (название файла указывается на после команды)"
}