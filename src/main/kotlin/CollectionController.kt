import commands.*
import exceptions.CommandNotFountException
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import requests.Request
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class CollectionController(private val collection: LinkedList<Organization>) { //todo solid
    companion object {
        fun checkUniquenessFullName(fullName: String?, collection: LinkedList<Organization>): Boolean {
            if (fullName == null)
                return true

            for (elem in collection)
                if (elem.getFullName() != null && elem.getFullName() == fullName)
                    return false
            return true
        }

        fun checkUniquenessId(id: Long, collection: LinkedList<Organization>): Boolean {
            if (!Organization.idIsValid(id))
                throw IllegalArgumentException("Невозможный id")

            for (elem in collection)
                if (elem.getId() == id)
                    return false
            return true
        }
    }

    private val commandMap = mutableMapOf<String, Command>()
    private val requests: Queue<Request> = LinkedList()

    private fun register(commandName: String, command: Command) {
        if (commandName.length > 40)
            throw Exception("Имя команды слишком длинное") //todo custom exceptions
        if (commandMap[commandName] != null)
            throw Exception("Имя команды уже зарезервировано")
        commandMap[commandName] = command
    }

    fun execute(commandData: CommandData) {
        if (commandData.commandName == null)
            return

        val command: Command = commandMap[commandData.commandName]
            ?: throw CommandNotFountException("${commandData.commandName}: команда не найдена")

        try {
            val (commandCompleted, request, message) = command.execute(commandData.args)
            if (commandCompleted) {
                requests.add(request)
                EventMessage.printMessage(message)
            }
            EventMessage.printMessage(message)
        } catch (e: InvalidArgumentsForCommandException) {
            EventMessage.printMessage(
                EventMessage.message(e.message.toString(), TextColor.RED)
            )
        }
    }

    private fun loadDataFromFile(fileName: String = "data.json") {
        val fileStream: FileInputStream
        try {
            fileStream = FileInputStream(fileName)
        } catch (e: FileNotFoundException) {
            EventMessage.printMessageln("${fileName}: ошибка во время открытия файла", TextColor.RED)
            EventMessage.printMessageln("Сообщение ошибки: $e", TextColor.RED)
            return
        }

        EventMessage.printMessageln("Чтение файла $fileName...")
        val fileReader = InputStreamReader(fileStream)
        val jsonCode: String = fileReader.readText()
        fileReader.close()
        EventMessage.printMessageln("Чтение завершено")
        EventMessage.printMessageln("Загрузка данных из файла $fileName в коллекцию...")
        val json = Json.decodeFromString<List<Organization>>(jsonCode)
        for (elem in json) {
            if (elem.objectIsValid() && checkUniquenessFullName(elem.getFullName(), collection) &&
                checkUniquenessId(elem.getId(), collection))
                collection.add(elem)
            else
                EventMessage.printMessageln("Элемент не подходит под требования коллекции", TextColor.RED)
        }
        EventMessage.printMessageln("Загрузка завершена")
    }

    fun loadDataFromFiles(files: Set<String>) {
        if (files.isEmpty()) {
            EventMessage.printMessage("ВНИМАНИЕ! На вход программы не передан ни один файл. ", TextColor.YELLOW)
            EventMessage.printMessageln("Загрузка данных из файла по умолчанию...")
            loadDataFromFile()
            return
        }

        for (file in files) {
            try {
                loadDataFromFile(file)
            } catch (e: Exception) {
                EventMessage.printMessageln("$file: произошла ошибка во время загрузки файла", TextColor.RED)
                EventMessage.printMessageln("Сообщение ошибки: ${e.message}", TextColor.RED)
            }
            println("\n")
        }
    }

    fun executeScript(script: String) {

    }

    fun enableInteractiveMode() {

    }

    init {
        register("help", HelpCommand(commandMap))
        register("info", InfoCommand(collection, Date(System.currentTimeMillis())))
        register("show", ShowCommand())
        register("add", AddCommand())
        register("update", UpdateCommand())
        register("remove_by_id", RemoveByIdCommand(collection))
        register("clear", ClearCommand())
        register("save", SaveCommand(collection))
        register("execute_script", ExecuteScriptCommand())
        register("exit", ExitCommand())
        register("remove_head", RemoveHeadCommand(collection))
        register("add_if_max", AddIfMaxCommand(collection, reader))
        register("remove_lower", RemoveLowerCommand(collection, reader))
        register("sum_of_employees_count", SumOfEmployeesCountCommand(collection))
        register("group_counting_by_employees_count", GroupCountingByEmployeesCountCommand(collection))
        register("print_unique_postal_address", PrintUniquePostalAddressCommand(collection))
    }
}