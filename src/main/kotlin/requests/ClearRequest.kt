package requests

import Organization
import exceptions.CancellationException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class ClearRequest : Request {
    private var oldCollection: LinkedList<Organization>? = null
    private var newCollection: LinkedList<Organization>? = null
    override fun process(collection: LinkedList<Organization>): String {
        oldCollection = LinkedList()
        oldCollection!!.addAll(collection)
        newCollection = collection

        collection.clear()
        return EventMessage.message("Коллекция очищена", TextColor.BLUE)
    }

    override fun cancel(): String {
        if (oldCollection == null || newCollection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")
        if (newCollection!!.isNotEmpty())
            throw CancellationException("Отмена запроса невозможна - коллекция уже была модифицирована")
        newCollection!!.addAll(oldCollection!!)
        oldCollection = null
        newCollection = null
        return "Запрос на очистку коллекции отменен"
    }
}