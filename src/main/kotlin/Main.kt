import command.CommandData
import iostreamers.EventMessage
import iostreamers.Reader
import java.util.*

fun main(args: Array<String>) {
    val collection = CollectionController(args.toSet())
    EventMessage.interactiveModeMessage()
    val reader = Reader(Scanner(System.`in`))
    var command: CommandData?
    do {
        EventMessage.inputPrompt(">>>", " ")
        command = reader.readCommand()
        if (command?.name == "")
            continue
        try {
            val message = collection.execute(command)
            if (message != null) EventMessage.printMessage(message)
        } catch (e: Exception) {
            EventMessage.oops()
        }
    } while (command != null && !(command.name == "exit" && command.args.args == null))
}