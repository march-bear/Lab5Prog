package requests

import CollectionController
import Organization
import collection.CollectionWrapper
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*
import kotlin.coroutines.cancellation.CancellationException

/**
 * Запрос на удаление элемента из коллекции по его id
 */
class RemoveByIdRequest(private val id: Long) : Request {
    private var removedElement: Organization? = null
    private var collection: CollectionWrapper<Organization>? = null
    override fun process(collection: CollectionWrapper<Organization>): String {
        removedElement = collection.find { it.id == id }

        if (collection.remove(removedElement!!))
            return EventMessage.message("Элемент удален", TextColor.BLUE)

        throw InvalidArgumentsForCommandException("Элемент с id $id не найден")
    }

    override fun cancel(): String {
        if (removedElement == null || collection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")

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