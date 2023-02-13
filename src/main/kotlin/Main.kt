import iostreamers.EventMessage
import java.util.LinkedList

fun main(args: Array<String>) {
    EventMessage.messageln("Создание коллекции...")
    val collection = CollectionController(LinkedList<Organization>())
    EventMessage.messageln("Коллекция создана")

    EventMessage.messageln("Загрузка данных из поданных на вход файлов...")
    collection.loadDataFromFiles(args)
    /*
    val o = Organization("1", Coordinates(1.0, 2),
        1, "1", 1, OrganizationType.COMMERCIAL, Address("1"))
    println(o)
    println(Json.encodeToString(o))
    println(Json.decodeFromString<Organization>(Json.encodeToString(o)))
     */
    collection.enableInteractiveMode()
}