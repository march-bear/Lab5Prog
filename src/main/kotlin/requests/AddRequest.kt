package requests

import IdManager
import Organization
import collection.CollectionWrapper
import exceptions.CancellationException
import iostreamers.Messenger
import iostreamers.TextColor

/**
 * Запрос на добавление нового элемента в коллекцию
 */
class AddRequest(
    private val element: Organization,
    private val idManager: IdManager,
) : Request {
    private var newElement: Organization? = null
    private var collection: CollectionWrapper<Organization>? = null
    override fun process(collection: CollectionWrapper<Organization>): Response {
        element.id = idManager.generateId()
            ?: return Response(
                false,
                Messenger.message("Коллекция переполнена", TextColor.RED),
            )

        collection.add(element.clone())
        newElement = element.clone()
        this.collection = collection

        return Response(
            true,
            Messenger.message("Элемент добавлен в коллекцию", TextColor.BLUE),
        )
    }

    override fun cancel(): String {
        if (newElement == null || collection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")

        val res = collection!!.remove(newElement!!)
        newElement = null
        collection = null

        if (res)
            return "Запрос на добавление элемента отменен"
        else
            throw CancellationException("Отмена запроса невозможна - добавленный элемент уже был подвергнут изменениям")
    }
}