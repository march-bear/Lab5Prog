package requests

import Organization
import collection.CollectionWrapper
import exceptions.CancellationException
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

/**
 * Запрос на очистку коллекции
 */
class ClearRequest : Request {
    private var oldCollection: CollectionWrapper<Organization>? = null
    private var newCollection: CollectionWrapper<Organization>? = null
    override fun process(collection: CollectionWrapper<Organization>): String {
        oldCollection = collection
        newCollection = collection
        collection.clear()
        return EventMessage.message("Коллекция очищена", TextColor.BLUE)
    }

    override fun cancel(): String {
        if (oldCollection == null || newCollection == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")
        if (!newCollection!!.isEmpty())
            throw CancellationException("Отмена запроса невозможна - коллекция уже была модифицирована")
        newCollection!!.addAll(oldCollection!!)
        oldCollection = null
        newCollection = null

        return "Запрос на очистку коллекции отменен"
    }
}