import collection.CollectionWrapper
import collection.LinkedListWrapper
import collection.ConcurrentLinkedQueue
import command.*
import command.implementations.*
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.FileNotFoundException
import kotlin.collections.HashMap

val commandQualifiers = listOf(
    "info", "show", "add", "update",
    "remove_by_id", "clear", "save",
    "execute_script", "exit", "remove_head",
    "add_if_max", "remove_lower",
    "sum_of_employees_count", "oops",
    "group_counting_by_employees_count",
    "print_unique_postal_address",
    "show_field_requirements",
    "change_collection_type", "rollback"
)

val basicCommandModule = module {
    single<Command>(named("help")) {
        val commandsMap = HashMap<String, String>()
        commandsMap["help"] = HelpCommand(mapOf()).info
        for (qualifier in commandQualifiers) {
            commandsMap[qualifier] = this.getKoin().get<Command>(named(qualifier)) {
                parametersOf(CollectionWrapper<Organization>(LinkedListWrapper()), null)
            }.info
        }
        HelpCommand(commandsMap)
    }

    factory<Command>(named("info")) { (collection: CollectionWrapper<Organization>) -> InfoCommand(collection) }

    factory<Command>(named("show")) { (collection: CollectionWrapper<Organization>) -> ShowCommand(collection) }

    factory<Command>(named("add")) {
            (
                _: CollectionWrapper<Organization>,
                controller: CollectionController?
            ) ->
        AddCommand(controller?.idManager)
    }

    single<Command>(named("update")) { UpdateCommand() }
    single<Command>(named("remove_by_id")) {
            (
                _: CollectionWrapper<Organization>,
                controller: CollectionController?
            ) -> RemoveByIdCommand(controller?.idManager)
    }
    single<Command>(named("clear")) { ClearCommand() }
    single<Command>(named("save")) {
            (_: CollectionWrapper<Organization>, controller: CollectionController?) -> SaveCommand(controller)
    }

    factory<Command>(named("execute_script")) {
            (
                collection: CollectionWrapper<Organization>,
                controller: CollectionController?
            ) -> ExecuteScriptCommand(collection, controller)
    }

    single<Command>(named("exit")) { ExitCommand() }
    single<Command>(named("remove_head")) { RemoveHeadCommand() }
    factory<Command>(named("add_if_max")) {
            (
                _: CollectionWrapper<Organization>,
                controller: CollectionController?
            ) ->
        AddIfMaxCommand(controller?.idManager)

    }
    single<Command>(named("remove_lower")) { RemoveLowerCommand() }

    factory<Command>(named("sum_of_employees_count")) {
            (collection: CollectionWrapper<Organization>) ->
        SumOfEmployeesCountCommand(collection)
    }

    factory<Command>(named("group_counting_by_employees_count")) {
            (collection: CollectionWrapper<Organization>) ->
        GroupCountingByEmployeesCountCommand(collection)
    }

    factory<Command>(named("print_unique_postal_address")) {
            (collection: CollectionWrapper<Organization>) -> PrintUniquePostalAddressCommand(collection)
    }

    single<Command>(named("oops")) { HackSystemCommand() }
    single<Command>(named("show_field_requirements")) { ShowFieldRequirementsCommand() }
    factory<Command>(named("change_collection_type")) {
            (collection: CollectionWrapper<Organization>) -> ChangeCollectionTypeCommand(collection)
    }

    factory<Command>(named("rollback")) {
            (
                _: CollectionWrapper<Organization>,
                controller: CollectionController?
            ) -> RollbackCommand(controller?.requestGraph)
    }
}

val basicCommandManagerModule = module {
    single {
        CommandManager(basicCommandModule, get(), get())
    }
}

val basicCollectionControllerModule = module {
    single { (fileName: String) -> try {
        CollectionController(fileName)
    } catch (ex: FileNotFoundException) { throw ex }
    }

    single<CollectionWrapper<Organization>> { CollectionWrapper(LinkedListWrapper()) }
}

val userCollectionControllerModule = module {
    single { (fileName: String) -> CollectionController(fileName) }

    single<CollectionWrapper<Organization>> { CollectionWrapper(ConcurrentLinkedQueue()) }
}