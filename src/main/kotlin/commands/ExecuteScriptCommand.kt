package commands

import exceptions.CyclicScriptCallException
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.ExecuteCommandsRequest
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*
import java.util.regex.Pattern


class ExecuteScriptCommand() : Command {
    override val info: String
        get() =
            "считать и исполнить скрипт из указанного файла (название файла указывается на после команды)"

    private val scriptFiles = Stack<String>()

    override fun execute(args: CommandArgument): CommandResult {
        val commandList: LinkedList<CommandData> = LinkedList()

        addCommandsFromFile("", commandList)

        return CommandResult(
            true,
            request = ExecuteCommandsRequest(commandList))
    }

    override val argumentTypes: List<ArgumentType>
        get() = TODO("Not yet implemented")

    private fun addCommandsFromFile(fileName: String, commandList: LinkedList<CommandData>) {
        if (fileName in this.scriptFiles)
            throw CyclicScriptCallException()
        scriptFiles.add(fileName)

        val listOfArgs = args?.split(Pattern.compile("\\s+"))
        if (listOfArgs?.size != 1) {
            EventMessage.printMessageln("Путь к файлу не указан", TextColor.RED)
            return
        }

        val script: String
        try {
            val inputStreamReader = InputStreamReader(FileInputStream(listOfArgs[0]))
            script = inputStreamReader.readText()
            inputStreamReader.close()
        } catch (e: FileNotFoundException) {
            EventMessage.printMessageln("${listOfArgs[0]}: ошибка во время открытия файл", TextColor.RED)
            EventMessage.printMessageln("Сообщение ошибки: $e", TextColor.RED)
            return
        }

        for (commandLine in script.split("\n")) {
            var (commandName, arguments) =
                (commandLine.trim() + " ").split(Pattern.compile("\\s+"), 2)
            arguments = arguments.trim()
            if (commandName == "execute_script") {
                addCommandsFromFile(arguments, commandList)
            } else
                commandList.add(CommandData(commandName, CommandArgument(
                    if (arguments == "") null else arguments
                )))
        }

        scriptFiles.pop()
    }
}