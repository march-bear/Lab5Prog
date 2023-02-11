import commands.CommandNotFountException
import commands.ExitCommandCall
import java.util.*

fun main(args: Array<String>) {
    val collection = CollectionInvoker(LinkedList<Organization>())
    val scanner = Scanner(System.`in`)

    EventMessage.yellowMessageln("Программа готова. " +
            "Для просмотра доступных команд воспользуйтесь командой \u001b[3m`help`\u001b[0m")
    while (true) {
        try {
            EventMessage.inputPrompt(delimiter = ">>> ")
            collection.execute(scanner.nextLine())
        } catch (e: CommandNotFountException) {
            EventMessage.redMessageln(e.message ?: "")
        } catch (e: ExitCommandCall) {
            EventMessage.yellowMessageln("Завершение работы программы...")
            EventMessage.yellowMessageln("Сохранение коллекции не происходит...")
            break
        } catch (e: NoSuchElementException) {
            EventMessage.yellowMessageln("Завершение работы программы...")
            EventMessage.yellowMessageln("Сохранение коллекции не происходит...")
            break
        } catch (e: RuntimeException) {
            EventMessage.yellowMessageln("Завершение работы программы...")
            EventMessage.yellowMessageln("Сохранение коллекции не происходит...")
            break
        }
    }
}