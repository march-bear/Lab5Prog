import commands.CommandArgument
import commands.CommandData
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.LinkedList

fun main(args: Array<String>) {
    val collection = CollectionController(args.toSet())

    collection.execute(CommandData("remove_head", CommandArgument("")))
    /*
    EventMessage.printMessageln("Коллекция создана")

    EventMessage.printMessageln("Загрузка данных из поданных на вход файлов...")
    try {
        collection.loadDataFromFiles(args.toSet())
    } catch (e: RuntimeException) {
        EventMessage.printMessageln("Произошла ошибка во время загрузки содержимого файла. " +
                "Возможно, предоставленные данные некорректны", TextColor.RED)
    }
    collection.enableInteractiveMode()
     */
}