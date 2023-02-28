package requests

import IdManager
import Organization
import exceptions.CancellationException
import exceptions.IdException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.lang.Exception
import java.util.*

class AddRequest(
    private val element: Organization,
    private val idManager: IdManager,
) : Request {
    private var newElement: Organization? = null
    private var collection: LinkedList<Organization>? = null
    override fun process(collection: LinkedList<Organization>): String {
        element.id = idManager.generateId() ?: throw IdException("Коллекция полностью заполнена")
        collection.add(element)
        return EventMessage.message("Элемент добавлен в коллекцию", TextColor.BLUE)
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