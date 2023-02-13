import iostreamers.EventMessage
import java.util.LinkedList

fun main(args: Array<String>) {
    val collection = CollectionController(LinkedList<Organization>())
    EventMessage.messageln("Коллекция создана")

    EventMessage.messageln("Загрузка данных из поданных на вход файлов...")
    collection.loadDataFromFiles(args)
    collection.enableInteractiveMode()
}