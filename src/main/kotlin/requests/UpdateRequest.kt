package requests

import Organization
import collection.CollectionWrapper
import exceptions.CancellationException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

/**
 * Запрос на обновление значения элемента по его id
 */
class UpdateRequest(
    private val id: Long,
    element: Organization,
) : Request {
    private var oldValue: Organization? = null
    private var collection: LinkedList<Organization>? = null
    private val newValue: Organization

    init {
        element.id = id
        newValue = element.clone()
    }
    override fun process(collection: CollectionWrapper<Organization>): String {/*
        try {
            val i = collection.indexOf(collection.find { it.id == id })
            newValue.id = id
            oldValue = collection[i].clone()
            collection[i] = newValue.clone()
            this.collection = collection
        } catch (e: IndexOutOfBoundsException) {
            return EventMessage.message("Элемент с id $id не найден", TextColor.RED)
        }
*/
        return EventMessage.message("Значение элемента с id $id обновлено", TextColor.BLUE)
    }

    override fun cancel(): String {
        if (oldValue == null || collection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")

        val i = collection!!.indexOf(collection!!.find { it.id == id })

        collection!![i] = oldValue!!
        oldValue = null
        collection = null

        return "Запрос на обновление значения элемента отменен"
    }
}