import command.CommandData
import iostreamers.Messenger
import iostreamers.Reader
import iostreamers.TextColor
import org.koin.core.context.startKoin
import org.koin.core.error.InstanceCreationException
import org.koin.core.parameter.parametersOf
import java.io.File
import java.io.FileNotFoundException
import java.util.*

fun main(args: Array<String>) {
    when (args.size) {
        0, 1 -> {
            val app = startKoin {
                modules(
                    basicCommandManagerModule,
                    userCollectionControllerModule,
                )
            }
            val file: File? =  if (args.isEmpty()) null else File(args[0])
            if (file != null) {
                if (file.exists()) {
                    if (file.isDirectory) {
                        Messenger.printMessage("${args[0]} - директория", TextColor.RED)
                        return
                    } else if (!file.canRead() || !file.canWrite()) {
                        Messenger.printMessage(
                            "${args[0]} - у пользователя недостаточно прав для работы с файлом",
                            TextColor.RED
                        )
                        return
                    }
                }
            }
            val controller: CollectionController
            try {
                controller = app.koin.get { parametersOf(file) }
            } catch (ex: InstanceCreationException) {
                if (ex.cause != null && ex.cause!!::class == FileNotFoundException::class) {
                    Messenger.printMessage(
                        "Ошибка во время открытия файла с коллекцией: ${ex.cause!!.message}", TextColor.RED
                    )
                    return
                }
                Messenger.printMessage(
                    "Контроллер не может быть инициализирован. Обратитесь к разработчику",
                    TextColor.RED
                )
                return
            }

            Messenger.interactiveModeMessage()
            val reader = Reader(Scanner(System.`in`))
            var command: CommandData?

            Messenger.printMessage(
                "\nДобро пожаловать в интерактивный режим! " +
                        "Для просмотра доступных команд введите `help`"
            )
            do {
                Messenger.inputPrompt(">>>", " ")
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
                    if (message != null) Messenger.printMessage(message)
                } catch (e: Exception) {
                    Messenger.printMessage(e.toString())
                    Messenger.printMessage(Messenger.oops())
                }
            } while (command != null && !(command.name == "exit" && command.args.primitiveTypeArguments == null))

            Messenger.printMessage("Завершение работы программы (сохранение коллекции не происходит)...")
        }
        else -> Messenger.printMessage(
            "Программа принимает один аргумент - путь к файлу с коллекцией.\n",
            TextColor.RED
        )
    }
}