import command.*
import command.CommandData
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.FileContentLoader
import iostreamers.TextColor
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.error.NoBeanDefFoundException
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import requests.Request
import java.util.*

class CollectionController(
    dataFiles: Set<String>,
) {
    companion object {
        fun checkUniquenessFullName(fullName: String?, collection: LinkedList<Organization>): Boolean {
            if (fullName == null)
                return true

            for (elem in collection)
                if (elem.fullName != null && elem.fullName == fullName)
                    return false
            return true
        }

        fun checkUniquenessId(id: Long, collection: LinkedList<Organization>): Boolean {
            if (!Organization.idIsValid(id))
                return false

            for (elem in collection)
                if (elem.id == id)
                    return false
            return true
        }
    }

    private val collection: LinkedList<Organization> = LinkedList()
    private val requests: Queue<Request> = LinkedList()
    val commandsApp: KoinApplication
    val idManager: IdManager
    val initializationDate = Date()

    fun execute(commandData: CommandData?) : String? {
        if (commandData?.name == null)
            return null

        val command: Command
        try {
            command = commandsApp.koin.get(named(commandData.name)) { parametersOf(collection, this) }
        } catch (e: NoBeanDefFoundException) {
            return EventMessage.message("${commandData.name}: команда не найдена", TextColor.RED)
        }

        val orgCount: Int
        try {
            orgCount = commandData.args.checkArguments(command.argumentTypes)
        } catch (ex: InvalidArgumentsForCommandException) {
            return EventMessage.message(ex.message.toString(), TextColor.RED)
        }

        val factory = OrganizationFactory()
        for (_i in 1..orgCount) {
            commandData.args.addOrganization(factory.newOrganizationFromInput())
        }
        try {
            val (commandCompleted, request, message) = command.execute(commandData.args)
            if (commandCompleted) {
                if (request != null)
                    requests.add(request)
            }
            EventMessage.printMessage(message)
        } catch (e: InvalidArgumentsForCommandException) {
            EventMessage.printMessage(
                EventMessage.message(e.message.toString(), TextColor.RED)
            )
        }

        while (requests.isNotEmpty()) {
            EventMessage.printMessage(requests.poll().process(collection))
        }

        return null
    }

    init {
        EventMessage.printMessage("Начало загрузки коллекции. Это может занять некоторое время...")

        val output = FileContentLoader(collection).loadDataFromFiles(dataFiles)

        EventMessage.printMessage("Загрузка коллекции завершена. Отчет о выполнении загрузки:")
        EventMessage.printMessage("---------------------------------------------------------------------")
        EventMessage.printMessage(output, newLine = false)
        EventMessage.printMessage("---------------------------------------------------------------------")

        commandsApp = startKoin {
            modules(commandsModule)
        }

        idManager = IdManager(collection)
    }
}