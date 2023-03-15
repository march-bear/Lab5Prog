import collection.CollectionWrapper
import command.CommandData
import command.Command
import commandcallgraph.RequestGraph
import exceptions.InvalidArgumentsForCommandException
import iostreamers.Messenger
import iostreamers.TextColor
import kotlinx.serialization.Polymorphic
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.error.InstanceCreationException
import requests.Request
import java.io.File
import java.util.*

class CollectionController(
    dataFileName: File? = null,
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
    val dataFileManager: DataFileManager = DataFileManager(collection, dataFileName)
    val commandManager: CommandManager by inject()
    val idManager: IdManager
    val requestGraph: RequestGraph = RequestGraph(collection)

    fun execute(commandData: CommandData?) : String? {
        if (commandData?.name == null)
            return null

        val (commandName, commandArguments) = commandData
        val command: Command
        try {
            command = commandManager.getCommand(commandName)
                ?: return Messenger.message("${commandName}: команда не найдена", TextColor.RED)
        } catch (ex: InstanceCreationException) {
            return Messenger.message("Объект команды не может быть получен. " +
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
                output += Messenger.message(message)

        } catch (ex: InvalidArgumentsForCommandException) {
            output += Messenger.message(ex.message ?: "", TextColor.RED)
        }

        while (requests.isNotEmpty()) {
            if (output != "")
                output += "\n"
            output += try {
                val request = requests.poll()
                val (requestCompleted, message, archivable) = request.process(collection)
                if (requestCompleted && archivable) {
                    val requestName = requestGraph.addLeaf(request, commandName)
                    Messenger.message("Запрос обработан, id запроса: $requestName\n", TextColor.BLUE) + message
                } else {
                    message
                }
            } catch (ex: InvalidArgumentsForCommandException) {
                Messenger.message(ex.message ?: "", TextColor.RED)
            }
        }


        return output
    }

    init {
        Messenger.printMessage("Начало загрузки коллекции. Это может занять некоторое время...")

        val output = dataFileManager.loadData()

        Messenger.printMessage("Загрузка коллекции завершена. Отчет о выполнении загрузки:")
        Messenger.printMessage("---------------------------------------------------------------------")
        Messenger.printMessage(output)
        Messenger.printMessage("---------------------------------------------------------------------\n")

        idManager = IdManager(collection)
    }
}