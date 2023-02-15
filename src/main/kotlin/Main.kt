import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.LinkedList

fun main(args: Array<String>) {
    val collection = CollectionController(LinkedList<Organization>())
    EventMessage.messageln("Коллекция создана")

    EventMessage.messageln("Загрузка данных из поданных на вход файлов...")
    try {
        collection.loadDataFromFiles(args.toSet())
    } catch (e: RuntimeException) {
        EventMessage.messageln("Произошла ошибка во время загрузки содержимого файла. " +
                "Возможно, предоставленные данные некорректны", TextColor.RED)
    }
    collection.enableInteractiveMode()
}