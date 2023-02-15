import commands.*
import exceptions.CommandNotFountException
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

/**
 * Класс, управляющий коллекцией и входным потоком и исполняющий вводимые пользователем команды
 */
class CollectionController(private val collection: LinkedList<Organization>) {
    companion object {
        /**
         * Проверяет переданное полное имя на уникальность в переданной коллекции
         */
        fun checkUniquenessFullName(fullName: String?, collection: LinkedList<Organization>): Boolean {
            if (fullName == null)
                return true

            for (elem in collection)
                if (elem.getFullName() != null && elem.getFullName() == fullName)
                    return false
            return true
        }

        /**
         * Проверяет переданное id на уникальность в переданной коллекции
         */
        fun checkUniquenessId(id: Long?, collection: LinkedList<Organization>): Boolean {
            if (!Organization.idIsValid(id))
                return false

            for (elem in collection)
                if (elem.getId() == id)
                    return false
            return true
        }
    }

    private val commandMap: HashMap<String, Command> = HashMap()
    private val scannerController: ScannerController = ScannerController(System.`in`)
    private val reader: Reader = Reader(scannerController)

    /**
     * Добавляет в commandMap пару Имя_команды: Объект_класса_команды
     */
    private fun register(commandName: String, command: Command) {
        if (commandName.length > 40)
            throw Exception("Имя команды слишком длинное")
        if (commandMap[commandName] != null)
            throw Exception("Имя команды уже зарезервировано")
        commandMap[commandName] = command
    }

    /**
     * Исполняет переданную в виде строки команду
     * @throws CommandNotFountException выбрасывается, если команда не зарегистрирована в commandMap
     */
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

    /**
     * Загружает данные для коллекции из файла
     * @param fileName имя файла
     */
    private fun loadDataFromFile(fileName: String = "data.json") {
        val fileStream: FileInputStream
        try {
            fileStream = FileInputStream(fileName)
        } catch (e: FileNotFoundException) {
            EventMessage.messageln("${fileName}: ошибка во время открытия файла", TextColor.RED)
            EventMessage.messageln("Сообщение ошибки: $e", TextColor.RED)
            return
        }

        EventMessage.messageln("Чтение файла $fileName...")
        val fileReader = InputStreamReader(fileStream)
        val jsonCode: String = fileReader.readText()
        fileReader.close()
        EventMessage.messageln("Чтение завершено")
        EventMessage.messageln("Загрузка данных из файла $fileName в коллекцию...")
        val json = Json.decodeFromString<List<Organization>>(jsonCode)
        for (elem in json) {
            if (elem.objectIsValid() && checkUniquenessFullName(elem.getFullName(), collection) &&
                checkUniquenessId(elem.getId(), collection))
                collection.add(elem)
            else
                EventMessage.messageln("Элемент не подходит под требования коллекции", TextColor.RED)
        }
        EventMessage.messageln("Загрузка завершена")
    }

    /**
     * Вызывает метод загрузки данных из каждого файла, содержащегося в поданном множестве
     */
    fun loadDataFromFiles(files: Set<String>) {
        if (files.isEmpty()) {
            EventMessage.message("ВНИМАНИЕ! На вход программы не передан ни один файл. ", TextColor.YELLOW)
            EventMessage.messageln("Загрузка данных из файла по умолчанию...")
            loadDataFromFile()
            return
        }

        for (file in files) {
            try {
                loadDataFromFile(file)
            } catch (e: Exception) {
                EventMessage.messageln("$file: произошла ошибка во время загрузки файла", TextColor.RED)
                EventMessage.messageln("Сообщение ошибки: ${e.message}", TextColor.RED)
            }
            println("\n")
        }
    }

    /**
     * Исполняет переданный в качестве параметра скрипт
     * @param script строка, содержащая скрипт
     */
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

    /**
     * Запускает интерактивный режим работы с коллекцией
     */
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
        register("info", InfoCommand(collection, Date(System.currentTimeMillis())))
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