import collection.CollectionWrapper
import command.CommandData
import command.Command
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.FileContentLoader
import iostreamers.TextColor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.error.DefinitionParameterException
import org.koin.core.error.InstanceCreationException
import requests.Request
import java.util.*

class CollectionController(
    dataFile: String? = null,
) : KoinComponent {
    companion object {
        fun checkUniquenessFullName(fullName: String?, collection: CollectionWrapper<Organization>): Boolean {
            if (fullName == null)
                return true

            for (elem in collection)
                if (elem.fullName != null && elem.fullName == fullName)
                    return false
            return true
        }

        fun checkUniquenessId(id: Long, collection: CollectionWrapper<Organization>): Boolean {
            if (!Organization.idIsValid(id))
                return false

            for (elem in collection)
                if (elem.id == id)
                    return false
            return true
        }
    }

    private val collection: CollectionWrapper<Organization> by inject()
    private val requests: Queue<Request> = LinkedList()
    val commandManager: CommandManager by inject()
    val idManager: IdManager

    fun execute(commandData: CommandData?) : String? {
        if (commandData?.name == null)
            return null

        val (commandName, commandArguments) = commandData
        val command: Command
        try {
            command = commandManager.getCommand(commandName)
                ?: return EventMessage.message("${commandName}: команда не найдена", TextColor.RED)
        } catch (ex: InstanceCreationException) {
            return EventMessage.message("Объект команды не может быть получен. " +
                    "Видимо, модули, используемые программой, некорректны.\n" +
                    "Обратитесь к разработчику: yasamofi404@gelch.com", TextColor.RED)
        }

        var output = ""
        try {
            val (commandCompleted, request, message) = command.execute(commandArguments)
            if (commandCompleted) {
                if (request != null)
                    requests.add(request)
            }

            if (message != null)
                output += EventMessage.message(message)

        } catch (ex: InvalidArgumentsForCommandException) {
            output += EventMessage.message(ex.message ?: "", TextColor.RED)
        }

        while (requests.isNotEmpty()) {
            if (output != "")
                output += "\n"
            output += EventMessage.message(requests.poll().process(collection))
        }

        return output
    }

    init {
        EventMessage.printMessage("Начало загрузки коллекции. Это может занять некоторое время...")

        val output = FileContentLoader(collection).loadDataFromFile()

        EventMessage.printMessage("Загрузка коллекции завершена. Отчет о выполнении загрузки:")
        EventMessage.printMessage("---------------------------------------------------------------------")
        EventMessage.printMessage(output, newLine = false)
        EventMessage.printMessage("---------------------------------------------------------------------")

        idManager = IdManager(collection)
    }
}