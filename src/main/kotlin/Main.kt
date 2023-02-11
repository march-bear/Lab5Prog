import java.util.*

fun main(args: Array<String>) {
    val collection = CollectionInvoker(LinkedList<Organization>())
    collection.execute("help")
    collection.execute("add")
    collection.execute("show")


    val scanner: Scanner = Scanner(System.`in`)
    try {
        while (true) {
            collection.execute(scanner.nextLine())
            break
        }
    } catch (e: Exception) {
        println("Выход из интерактивного режима...")
    }
}