import command.*
import command.implementations.*
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*
import kotlin.collections.HashMap

val qualifiers = listOf(
    "info", "show", "add", "update",
    "remove_by_id", "clear", "save",
    "execute_script", "exit", "remove_head",
    "add_if_max", "remove_lower",
    "sum_of_employees_count", "oops",
    "group_counting_by_employees_count",
    "print_unique_postal_address",
    "show_field_requirements",
)

val commandsModule = module {
    factory<Command>(named("help")) {
        val commandsMap = HashMap<String, String>()
        commandsMap["help"] = HelpCommand(mapOf()).info
        for (qualifier in qualifiers)
            commandsMap[qualifier] = this.getKoin().get<Command>(named(qualifier)) {
                parametersOf(LinkedList<Organization>(), null)
            }.info

        HelpCommand(commandsMap)
    }

    factory<Command>(named("info")) {
            (
                collection: LinkedList<Organization>,
                controller: CollectionController?
            ) ->
        InfoCommand(
            collection.size,
            collection.maxOrNull()?.id,
            collection.minOrNull()?.id,
            controller?.initializationDate
        )
    }

    factory<Command>(named("show")) {(collection: LinkedList<Organization>) ->
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
            (
                collection: LinkedList<Organization>,
                controller: CollectionController?
            ) ->
        ExecuteScriptCommand(collection, controller)
    }

    single<Command>(named("exit")) { ExitCommand() }
    single<Command>(named("remove_head")) { RemoveHeadCommand() }
    factory<Command>(named("add_if_max")) { AddIfMaxCommand(OrganizationFactory()) }
    factory<Command>(named("remove_lower")) { RemoveLowerCommand(OrganizationFactory()) }

    factory<Command>(named("sum_of_employees_count")) {
            (collection: LinkedList<Organization>) ->
        SumOfEmployeesCountCommand(collection.sumOf { it.employeesCount ?: 0 })
    }

    factory<Command>(named("group_counting_by_employees_count")) {
            (collection: LinkedList<Organization>) ->
        GroupCountingByEmployeesCountCommand(collection.groupBy { it.employeesCount })
    }

    factory<Command>(named("print_unique_postal_address")) {(collection: LinkedList<Organization>) ->
        PrintUniquePostalAddressCommand(
            collection.map { it.postalAddress.toString() }.toSet()
        )
    }

    single<Command>(named("oops")) { HackSystemCommand() }
    single<Command>(named("show_field_requirements")) { ShowFieldRequirementsCommand() }
}