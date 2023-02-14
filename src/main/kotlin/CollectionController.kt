import commands.*
import exceptions.CommandNotFountException
import exceptions.ExitCommandCall
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.internal.decodeStringToJsonTree
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

class CollectionController(private val collection: LinkedList<Organization>) {
    companion object {
        fun checkUniquenessFullName(fullName: String?, collection: LinkedList<Organization>): Boolean {
            if (fullName == null)
                return true

            for (elem in collection)
                if (elem.getFullName() != null && elem.getFullName() == fullName)
                    return false
            return true
        }
    }

    private val commandMap: HashMap<String, Command> = HashMap()
    private val scannerController: ScannerController = ScannerController(System.`in`)
    private val reader: Reader = Reader(scannerController)


    private fun register(commandName: String, command: Command) {
        if (commandName.length > 40)
            throw Exception("Имя команды слишком длинное")
        if (commandMap[commandName] != null)
            throw Exception("Имя команды уже зарезервировано")
        commandMap[commandName] = command
    }

    fun execute(commandName: String) {
        val commandList = commandName.trim().split(Pattern.compile("\\s+"), limit = 2)
        if (commandList.isEmpty() || commandList[0] == "")
            return

        val command: Command = commandMap[commandList[0]]
            ?: throw CommandNotFountException("${commandList[0]}: команда не найдена")

        try {
            command.execute(if (commandList.size == 2) commandList[1] else null)
        } catch (e: InvalidArgumentsForCommandException) {
            EventMessage.messageln(e.message.toString(), TextColor.RED)
        }
    }

    private fun loadDataFromFile(fileName: String = "data.json") {
        val fileStream: FileInputStream
        try {
            fileStream = FileInputStream(fileName)
        } catch (e: FileNotFoundException) {
            EventMessage.messageln("${fileName}: файл не найден", TextColor.RED)
            return
        }

        EventMessage.messageln("Чтение файла $fileName")
        val fileReader = InputStreamReader(fileStream)
        val jsonCode: String = fileReader.readText()
        fileReader.close()
        EventMessage.messageln("Чтение завершено")
        EventMessage.messageln("Загрузка данных файла $fileName в коллекцию")
        val json = Json.decodeFromString<List<Organization>>(jsonCode)
        for (elem in json)
            collection.add(elem)
        EventMessage.messageln("Загрузка завершена")
    }

    fun loadDataFromFiles(files: Set<String>) {
        if (files.isEmpty()) {
            EventMessage.messageln("ВНИМАНИЕ! На вход программы не передан ни один файл. " +
                    "Загрузка данных из файла по умолчанию...", TextColor.YELLOW)
            loadDataFromFile()
            return
        }

        for (file in files) {
            try {
                loadDataFromFile(file)
            } catch (e: Exception) {
                println(e.message)
            }
            println()
        }
    }

    fun executeScript(script: String) {
        EventMessage.messageln("Запущено исполнение скрипта", TextColor.YELLOW)
        scannerController.scanner = Scanner(script)
        while (scannerController.scanner.hasNext()) {
            try {
                execute(scannerController.scanner.nextLine().trim())
            } catch (e: CommandNotFountException) {
                EventMessage.messageln(e.message ?: "", TextColor.RED)
            } catch (e: RuntimeException) {
                EventMessage.messageln(e.message.toString())
            }
        }
        EventMessage.messageln("Скрипт выполнен", TextColor.YELLOW)
        scannerController.scanner = Scanner(System.`in`)
    }

    fun enableInteractiveMode() {
        EventMessage.interactiveModeMessage()
        EventMessage.messageln("Включен интерактивный режим. " +
                "Для просмотра доступных команд введите \u001b[3m`help`\u001b[0m", TextColor.YELLOW)
        scannerController.scanner = Scanner(System.`in`)
        while (true) {
            try {
                EventMessage.inputPrompt(delimiter = ">>> ")
                execute(scannerController.scanner.nextLine().trim())
            } catch (e: CommandNotFountException) {
                EventMessage.messageln(e.message ?: "", TextColor.RED)
            } catch (e: RuntimeException) {
                println(e.message)
                EventMessage.messageln("Завершение работы программы...")
                EventMessage.messageln("Сохранение коллекции не происходит...")
                break
            } catch (e: Throwable) {
                EventMessage.oops()
            }
        }
    }

    init {
        register("help", HelpCommand(commandMap))
        register("info", InfoCommand(collection))
        register("show", ShowCommand(collection))
        register("add", AddCommand(collection, reader))
        register("update", UpdateCommand(collection, reader))
        register("remove_by_id", RemoveByIdCommand(collection))
        register("clear", ClearCommand(collection))
        register("save", SaveCommand(collection))
        register("execute_script", ExecuteScriptCommand(this, scannerController.getInputStream()))
        register("exit", ExitCommand())
        register("remove_head", RemoveHeadCommand(collection))
        register("add_if_max", AddIfMaxCommand(collection, reader))
        register("remove_lower", RemoveLowerCommand(collection, reader))
        register("sum_of_employees_count", SumOfEmployeesCountCommand(collection))
        register("group_counting_by_employees_count", GroupCountingByEmployeesCountCommand(collection))
        register("print_unique_postal_address", PrintUniquePostalAddressCommand(collection))
    }
}