import commands.*
import exceptions.CommandNotFountException
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.FileContentLoader

import iostreamers.TextColor
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.component.getScopeName
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import requests.Request
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class CollectionController(
    dataFiles: Set<String>,
) {
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

    private val collection: LinkedList<Organization> = LinkedList()
    private val requests: Queue<Request> = LinkedList()
    private val app: KoinApplication

    fun execute(commandData: CommandData) {
        if (commandData.commandName == null)
            return

        val command: Command = app.koin.get<Command>(named(commandData.commandName))

        try {
            val (commandCompleted, request, message) = command.execute(commandData.args)
            if (commandCompleted) {
                requests.add(request)
            }
            EventMessage.printMessage(message)
        } catch (e: InvalidArgumentsForCommandException) {
            println("КАКОГО")
            EventMessage.printMessage(
                EventMessage.message(e.message.toString(), TextColor.RED)
            )
        }
    }

    init {
        EventMessage.printMessage("Начало загрузки коллекции. Это может занять некоторое время...")

        val output = FileContentLoader(collection).loadDataFromFiles(dataFiles)

        EventMessage.printMessage("Загрузка коллекции завершена. Отчет о выполнении загрузки:")
        EventMessage.printMessage("---------------------------------------------------------------------")
        EventMessage.printMessage(output)
        EventMessage.printMessage("---------------------------------------------------------------------")

        val singleModule = module {
            factory<Command>(named("help")) {
                HelpCommand(
                    this.getKoin().getAll<Command>().associate { it.info to it.info }
                )
            }

            factory<Command>(named("info")) {
                InfoCommand(
                    collection.size,
                    collection.max().getId(),
                    collection.min().getId(),
                    Date(),
                )
            }

            factory<Command>(named("show")) {
                ShowCommand(
                    collection.map { it.toString() }
                )
            }

            factory<Command>(named("add")) { AddCommand(OrganizationFactory()) }
            factory<Command>(named("update")) { UpdateCommand(OrganizationFactory()) }
            single<Command>(named("remove_by_id")) { RemoveByIdCommand() }
            single<Command>(named("clear")) { ClearCommand() }
            factory<Command>(named("save")) { SaveCommand() }

            factory<Command>(named("execute_script")) {
                ExecuteScriptCommand(
                    this@CollectionController
                )
            }

            single<Command>(named("exit")) { ExitCommand() }
            single<Command>(named("remove_head")) { RemoveHeadCommand() }
            factory<Command>(named("add_if_max")) { AddIfMaxCommand(OrganizationFactory()) }
            factory<Command>(named("remove_lower")) { RemoveLowerCommand(OrganizationFactory()) }

            factory<Command>(named("sum_of_employees_count")) {
                SumOfEmployeesCountCommand(collection.sumOf { it.getId() })
            }

            factory<Command>(named("group_counting_by_employees_count")) {
                GroupCountingByEmployeesCountCommand(collection.groupBy { it.getEmployeesCount() })
            }

            factory<Command>(named("print_unique_postal_address")) {
                PrintUniquePostalAddressCommand(
                    collection.map { it.getPostalAddress()!!.zipCode }.toSet()
                )
            }

            single<Command>(named("oops")) { HackSystemCommand() }

        }
        app = startKoin {
            modules(singleModule)
        }
    }
}