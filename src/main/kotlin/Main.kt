import command.CommandData
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import java.util.*

fun main(args: Array<String>) {
    when (args.size) {
        1 -> {
            val app = startKoin {
                modules(
                    basicCommandManagerModule,
                    userCollectionControllerModule,
                )
            }

            val controller = app.koin.get<CollectionController> { parametersOf(args[0]) }

            EventMessage.interactiveModeMessage()
            val reader = Reader(Scanner(System.`in`))
            var command: CommandData?

            EventMessage.printMessage(
                "\nДобро пожаловать в интерактивный режим! " +
                        "Для просмотра доступных команд введите `help`"
            )
            do {
                EventMessage.inputPrompt(">>>", " ")
                command = reader.readCommand()
                if (command == null || command.name == "")
                    continue
                if (command.args.organizationLimit != 0) {
                    try {
                        val factory = OrganizationFactory()
                        for (_i in 1..command.args.organizationLimit) {
                            command.args.addOrganization(factory.newOrganizationFromInput())
                        }
                    } catch (ex: NoSuchElementException) { break }
                }

                try {
                    val message = controller.execute(command)
                    if (message != null) EventMessage.printMessage(message)
                } catch (e: Exception) {
                    EventMessage.message(e.toString())
                    EventMessage.printMessage(EventMessage.oops())
                }
            } while (command != null && !(command.name == "exit" && command.args.primitiveTypeArguments == null))

            EventMessage.printMessage("Завершение работы программы (сохранение коллекции не происходит)...")
        }
        else -> EventMessage.printMessage(
            "Программа принимает один аргумент - путь к файлу с коллекцией.\n",
            TextColor.RED
        )
    }
}