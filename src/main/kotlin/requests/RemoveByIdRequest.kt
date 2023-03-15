package requests

import CollectionController
import IdManager
import Organization
import collection.CollectionWrapper
import exceptions.InvalidArgumentsForCommandException
import iostreamers.Messenger
import iostreamers.TextColor
import kotlin.coroutines.cancellation.CancellationException

/**
 * Запрос на удаление элемента из коллекции по его id
 */
class RemoveByIdRequest(private val id: Long, private val idManager: IdManager) : Request {
    private var removedElement: Organization? = null
    private var collection: CollectionWrapper<Organization>? = null
    override fun process(collection: CollectionWrapper<Organization>): Response {
        removedElement = collection.find { it.id == id }

        if (removedElement != null) {
            collection.remove(removedElement!!)
            this.collection = collection
            return Response(true, Messenger.message("Элемент удален", TextColor.BLUE))
        }

        return Response(false, Messenger.message("Элемент с id $id не найден", TextColor.RED))
    }

    override fun cancel(): String {
        if (removedElement == null || collection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")

        if (!CollectionController.checkUniquenessFullName(removedElement!!.fullName, collection!!))
            throw CancellationException("Отмена запроса невозможна, так как в коллекции уже есть элемент с таким же " +
                    "id или полным именем")

        removedElement!!.id = idManager.generateId()!!
        collection!!.add(removedElement!!)
        removedElement = null
        collection = null
        return "Запрос на удаление элемента отменен"
    }
}