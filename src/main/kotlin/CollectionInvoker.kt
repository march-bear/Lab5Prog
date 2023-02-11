import commands.*
import java.lang.Exception
import java.util.LinkedList

class CollectionInvoker(private val collection: LinkedList<Organization>) {
    private val commandMap: HashMap<String, Command> = HashMap()

    private fun register(commandName: String, command: Command) {
        if (commandName.length > 40)
            throw Exception("Имя команды слишком длинное")
        if (commandMap[commandName] != null)
            throw Exception("Имя команды уже зарезервировано")
        commandMap[commandName] = command
    }

    fun execute(commandName: String) {

        val command: Command = commandMap[commandName]
            ?: throw CommandNotFountException("$commandName: команда не найдена")

        command.execute("")
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
        register("execute_script", ExecuteScriptCommand())
        register("exit", ExitCommand())
        register("remove_head", RemoveHeadCommand(collection))
        register("sum_of_employees_count", SumOfEmployeesCountCommand())
        register("group_counting_by_employees_count", GroupCountingByEmployeesCountCommand())
        register("print_unique_postal_address", PrintUniquePostalAddressCommand())
    }
}