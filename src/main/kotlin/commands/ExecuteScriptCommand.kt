package commands

import CollectionController
import InputStreamController
import iostreamers.EventMessage
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.regex.Pattern

class ExecuteScriptCommand(private var inputStreamController: InputStreamController,
                           private var collection: CollectionController) : Command {
    override fun execute(args: String?) {
        val listOfArgs = args?.split(Pattern.compile("\\s+"))
        if (listOfArgs?.size != 1) {
            EventMessage.messageln("Путь к файлу не указан", TextColor.RED)
            return
        }

        try {
            inputStreamController.inputStream = FileInputStream(listOfArgs[0])
        } catch (e: FileNotFoundException) {
            EventMessage.messageln("${listOfArgs[0]}: файл не найден", TextColor.RED)
            return
        }
        val inputStreamReader = InputStreamReader(inputStreamController.inputStream)
        val script: String = inputStreamReader.readText()
        inputStreamReader.close()
        println(script)
        collection.executeScript(script)
        inputStreamController.inputStream = System.`in`
    }

    override fun getInfo(): String =
        "считать и исполнить скрипт из указанного файла (название файла указывается на после команды)"
}