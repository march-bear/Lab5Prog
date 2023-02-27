package command.implementations

import CollectionController
import Organization
import command.*
import exceptions.ScriptException
import iostreamers.EventMessage
import iostreamers.TextColor
import requests.ExecuteCommandsRequest
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*
import java.util.regex.Pattern


class ExecuteScriptCommand(
    private val collection: LinkedList<Organization>,
    private val controller: CollectionController?,
    private val executeScriptCommandString: String = "execute_script",
) : Command {
    override val info: String
        get() =
            "считать и исполнить скрипт из указанного файла (название файла указывается на после команды)"

    override val argumentTypes: List<ArgumentTypeData>
        get() = listOf(
            ArgumentTypeData(ArgumentType.STRING, false)
        )

    private val scriptFiles = Stack<String>()

    override fun execute(args: CommandArgument): CommandResult {
        if (controller == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")
        args.checkArguments(argumentTypes)

        val commandList: LinkedList<CommandData> = LinkedList()
        val fileName: String = args.args?.get(0) ?: ""

        try {
            addCommandsFromFile(fileName, commandList)
        }  catch (e: FileNotFoundException) {
            var output = "$fileName: ошибка во время открытия файла\n" +
                    "Сообщение ошибки: $e\n" +
                    "Сводка о вложенных вызовах скриптов:"

            scriptFiles.forEach { output += "\n $it ->" }
            output += " ERROR"

            return CommandResult(
                false,
                message = EventMessage.message(output, TextColor.RED)
            )
        }

        return CommandResult(
            true,
            request = ExecuteCommandsRequest(commandList, controller),
            message = EventMessage.message("Запрос на исполнение скрипта отправлен", TextColor.BLUE),
        )
    }

    private fun addCommandsFromFile(fileName: String, commandList: LinkedList<CommandData>) {
        if (fileName in this.scriptFiles) {
            var message = "Обнаружен циклический вызов скрипта:"

            for (i in scriptFiles.indexOf(fileName) until scriptFiles.size)
                message += "\n ${scriptFiles[i]} ->"
            throw ScriptException("$message $fileName")
        }

        scriptFiles.add(fileName)

        val script: String
        val inputStreamReader = InputStreamReader(FileInputStream(fileName))
        script = inputStreamReader.readText()
        inputStreamReader.close()

        for (commandLine in script.split("\n")) {
            val (commandName, argumentsLine) =
                (commandLine.trim() + " ").split(Pattern.compile("\\s+"), 2)
            val arguments = CommandArgument(if (argumentsLine.trim() == "") null else argumentsLine.trim())
            if (commandName == executeScriptCommandString) {
                if (this.scriptFiles.size > MAXIMUM_NESTED_SCRIPTS_CALLS)
                    throw ScriptException(
                        "Превышено максимальное количество вложенных вызовов скриптов: $MAXIMUM_NESTED_SCRIPTS_CALLS"
                    )
                arguments.checkArguments(argumentTypes)
                addCommandsFromFile(arguments.args?.get(0) ?: "", commandList)
            } else
                commandList.add(
                    CommandData(
                        commandName,
                        arguments,
                    )
                )
        }

        scriptFiles.pop()
    }

    companion object {
        private const val MAXIMUM_NESTED_SCRIPTS_CALLS: Int = 10
    }
}