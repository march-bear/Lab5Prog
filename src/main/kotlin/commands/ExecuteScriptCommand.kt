package commands

import CollectionController
import ScannerController
import iostreamers.EventMessage
import iostreamers.TextColor
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Pattern

/**
 * Класс команды execute_script для исполнения скрипта из файла
 */
class ExecuteScriptCommand(private val collection: CollectionController,
                           private val ScannerController: ScannerController) : Command {
    override fun execute(args: String?) {
        val listOfArgs = args?.split(Pattern.compile("\\s+"))
        if (listOfArgs?.size != 1) {
            EventMessage.messageln("Путь к файлу не указан", TextColor.RED)
            return
        }

        if (ScannerController.getInputStream() != System.`in`)
            throw java.lang.RuntimeException("Прервана попытка запуска скрипта во время исполнения другого скрипта")

        val script: String
        try {
            val inputStreamReader = InputStreamReader(FileInputStream(listOfArgs[0]))
            script = inputStreamReader.readText()
            inputStreamReader.close()
        } catch (e: FileNotFoundException) {
            EventMessage.messageln("${listOfArgs[0]}: ошибка во время открытия файл", TextColor.RED)
            EventMessage.messageln("Сообщение ошибки: $e", TextColor.RED)
            return
        }

        collection.executeScript(script)
    }

    override fun getInfo(): String =
        "считать и исполнить скрипт из указанного файла (название файла указывается на после команды)"
}