package requests

import Organization
import exceptions.CancellationException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

/**
 * Запрос на удаление первого элемента в коллекции
 */
class RemoveHeadRequest : Request {
    private var removedElement: Organization? = null
    private var collection: LinkedList<Organization>? = null

    override fun process(collection: LinkedList<Organization>): String {
        this.collection = collection
        if (collection.isEmpty())
            return EventMessage.message("Элемент не может быть удален - коллекция пуста", TextColor.RED)

        removedElement = collection.first.clone()
        return collection.removeFirst().toString() + EventMessage.message("\nЭлемент удален", TextColor.BLUE)
    }

    override fun cancel(): String {
        if (collection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")

        if (removedElement == null)
            return "Запрос на удаление элемента отменен"

        if (CollectionController.checkUniquenessFullName(removedElement!!.fullName, collection!!) ||
            collection!!.find { it.id == removedElement!!.id } != null)
            throw CancellationException("Отмена запроса невозможна, так как в коллекции уже есть элемент с таким же " +
                    "id или полным именем")

        collection!!.add(removedElement!!)
        removedElement = null
        collection = null
        return "Запрос на удаление элемента отменен"
    }
}