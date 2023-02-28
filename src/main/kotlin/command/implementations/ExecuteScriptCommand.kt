package command.implementations

import CollectionController
import Organization
import OrganizationFactory
import command.*
import exceptions.ScriptException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import org.koin.core.qualifier.named
import requests.ExecuteCommandsRequest
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*
import java.util.regex.Pattern


class ExecuteScriptCommand(
    private val collection: LinkedList<Organization>,
    private val controller: CollectionController?,
) : Command {
    override val info: String
        get() =
            "считать и исполнить скрипт из указанного файла (название файла указывается на после команды)"

    override val argumentTypes: List<ArgumentType>
        get() = listOf(ArgumentType.STRING)

    private val scriptFiles = Stack<String>()

    override fun execute(args: CommandArgument): CommandResult {
        if (controller == null)
            return CommandResult(false, message = "Объект команды не предназначен для исполнения")

        args.checkArguments(argumentTypes)

        val commandList: LinkedList<Pair<Command, CommandArgument>> = LinkedList()
        val fileName: String = args.primitiveTypeArguments?.get(0)!!

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
            request = ExecuteCommandsRequest(commandList, controller, collection),
            message = EventMessage.message("Запрос на исполнение скрипта отправлен", TextColor.BLUE),
        )
    }

    private fun addCommandsFromFile(fileName: String, commandList: LinkedList<Pair<Command, CommandArgument>>) {
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

        val reader = Reader(Scanner(script))
        var currLine: ULong = 1UL

        var commandData = reader.readCommand()
        while (commandData != null) {
            val (commandName, commandArguments) = commandData
            val command = controller!!.commandsApp.koin.get<Command>(named(commandName ?: ""))

            for (i in 1..commandArguments.checkArguments(command.argumentTypes)) {
                commandArguments.organizations.add(OrganizationFactory(reader).newOrganizationFromInput())
                currLine += 8UL
            }

            if (command::class == this::class) {
                if (this.scriptFiles.size > MAXIMUM_NESTED_SCRIPTS_CALLS)
                    throw ScriptException(
                        "Превышено максимальное количество вложенных вызовов скриптов: $MAXIMUM_NESTED_SCRIPTS_CALLS"
                    )

                addCommandsFromFile(commandArguments.primitiveTypeArguments?.get(0) ?: "", commandList)
            } else {
                commandList.add(Pair(command, commandArguments))
            }
            commandData = reader.readCommand()
            currLine++
        }

        scriptFiles.pop()
    }

    companion object {
        private const val MAXIMUM_NESTED_SCRIPTS_CALLS: Int = 10
    }
}