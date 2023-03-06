import collection.CollectionWrapper
import command.Command
import org.koin.core.error.DefinitionParameterException
import org.koin.core.error.InstanceCreationException
import org.koin.core.error.NoBeanDefFoundException
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import java.util.*

class CommandManager(
    private val module: Module,
    private val collection: CollectionWrapper<Organization>,
    private val controller: CollectionController,
) {
    private val koinApp = koinApplication {
        modules(module)
    }

    fun getCommand(name: String): Command? = try {
            koinApp.koin.get(named(name)) { parametersOf(collection, controller) }
        } catch (ex: NoBeanDefFoundException) {
            null
        }
}