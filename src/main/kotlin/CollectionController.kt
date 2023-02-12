import commands.*
import exceptions.CommandNotFountException
import exceptions.ExitCommandCall
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

class CollectionController(private val collection: LinkedList<Organization>) {
    private val commandMap: HashMap<String, Command> = HashMap()
    private var inputStreamController: InputStreamController = InputStreamController()

    private fun register(commandName: String, command: Command) {
        if (commandName.length > 40)
            throw Exception("Имя команды слишком длинное")
        if (commandMap[commandName] != null)
            throw Exception("Имя команды уже зарезервировано")
        commandMap[commandName] = command
    }

    fun execute(commandName: String) {
        val commandList = commandName.split(Pattern.compile("\\s+"), limit = 2)
        if (commandList.isEmpty() || commandList[0] == "")
            return

        val command: Command = commandMap[commandList[0]]
            ?: throw CommandNotFountException("${commandList[0]}: команда не найдена")

        command.execute(if (commandList.size == 2) commandList[1] else "")
    }

    fun executeScript(script: String) {
        EventMessage.yellowMessageln("Запущено исполнение скрипта")
        val scanner = Scanner(script)
        while (scanner.hasNext()) {
            try {
                execute(scanner.nextLine().trim())
            } catch (e: CommandNotFountException) {
                EventMessage.redMessageln(e.message ?: "")
            } catch (e: ExitCommandCall) {
                EventMessage.yellowMessageln("Завершение работы программы...")
                EventMessage.yellowMessageln("Сохранение коллекции не происходит...")
                break
            }
        }
        EventMessage.yellowMessageln("Скрипт выполнен")
    }

    fun enableInteractiveMode(script: String? = null) {
        EventMessage.interactiveMode()
        EventMessage.yellowMessageln("Включен интерактивный режим. " +
                "Для просмотра доступных команд воспользуйтесь командой \u001b[3m`help`\u001b[0m")
        val scanner = if (script != null) Scanner(script) else Scanner(System.`in`)
        while (true) {
            try {
                EventMessage.inputPrompt(delimiter = ">>> ")
                execute(scanner.nextLine().trim())
            } catch (e: CommandNotFountException) {
                EventMessage.redMessageln(e.message ?: "")
            } catch (e: ExitCommandCall) {
                EventMessage.yellowMessageln("Завершение работы программы...")
                EventMessage.yellowMessageln("Сохранение коллекции не происходит...")
                break
            } catch (e: NoSuchElementException) {
                EventMessage.yellowMessageln("Завершение работы программы...")
                EventMessage.yellowMessageln("Сохранение коллекции не происходит...")
                break
            } catch (e: RuntimeException) {
                EventMessage.yellowMessageln("Завершение работы программы...")
                EventMessage.yellowMessageln("Сохранение коллекции не происходит...")
                break
            }
        }
    }

    init {
        register("help", HelpCommand(commandMap))
        register("info", InfoCommand(collection))
        register("show", ShowCommand(collection))
        register("add", AddCommand(collection))
        register("update", UpdateCommand())
        register("remove_by_id", RemoveByIdCommand())
        register("clear", ClearCommand(collection))
        register("save", SaveCommand())
        register("execute_script", ExecuteScriptCommand(inputStreamController, this))
        register("exit", ExitCommand())
        register("remove_head", RemoveHeadCommand(collection))
        register("sum_of_employees_count", SumOfEmployeesCountCommand())
        register("group_counting_by_employees_count", GroupCountingByEmployeesCountCommand())
        register("print_unique_postal_address", PrintUniquePostalAddressCommand())
    }
}