package requests

import Organization
import exceptions.CancellationException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

/**
 * Запрос на удаление из коллекции всех элементов, меньших, чем данный
 */
class RemoveLowerRequest(private val element: Organization) : Request {
    private val removedElements: LinkedList<Organization> = LinkedList()
    private var collection: LinkedList<Organization>? = null
    override fun process(collection: LinkedList<Organization>): String {
        var i = 0
        var output = ""
        while (i < collection.size) {
            if (collection[i] < element) {
                output += EventMessage.message(
                    "Удален элемент с id ${collection.removeAt(i).id}\n",
                    TextColor.BLUE
                )
                continue
            }
            i++
        }

        this.collection = collection
        if (output != "")
            return output

        return EventMessage.message("В коллекции нет элементов, меньших, чем введенный", TextColor.BLUE)
    }

    override fun cancel(): String {
        if (collection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")

        for (removedElement in removedElements) {
            if (CollectionController.checkUniquenessFullName(removedElement.fullName, collection!!) ||
                collection!!.find { it.id == removedElement.id } != null
            )
                throw CancellationException(
                    "Отмена запроса невозможна, так как в коллекции уже есть элемент с таким же " +
                            "id или полным именем"
                )
        }

        for (removedElement in removedElements)
            collection!!.add(removedElement)
        removedElements.clear()
        collection = null
        return "Запрос на удаление элементов отменен"
    }
}