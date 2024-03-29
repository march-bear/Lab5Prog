package requests

import Organization
import collection.CollectionWrapper
import commandcallgraph.RequestGraph
import exceptions.CancellationException
import iostreamers.Messenger
import iostreamers.TextColor

class RollbackRequest(private val requestGraph: RequestGraph, private val id: String): Request {
    private var oldCurrLeafId: String? = null
    private var currLeafId: String? = null
    override fun process(collection: CollectionWrapper<Organization>): Response {
        oldCurrLeafId = requestGraph.getCurrLeafId()
        return if (requestGraph.rollback(id)) {
            currLeafId = requestGraph.getCurrLeafId()
            Response(
                true,
                Messenger.message("Коллекции возвращено состояние $id", TextColor.BLUE),
                false
            )
        }
        else {
            oldCurrLeafId = null
            Response(false, Messenger.message("Запрос с id $id не найден", TextColor.RED))
        }
    }

    override fun cancel(): String {
        if (oldCurrLeafId == null || currLeafId == null)
            throw CancellationException("Отмена запроса невозможна, так как он ещё не был выполнен или уже был отменен")

        if (requestGraph.getCurrLeafId() != currLeafId)
            throw CancellationException("Запрос не может быть отменен, т. к. текущий элемент в графе изменился")

        if (requestGraph.rollback(oldCurrLeafId!!)) {
            currLeafId = null
            oldCurrLeafId = null
            return Messenger.message("Запрос на rollback отменен", TextColor.BLUE)
        } else {
            oldCurrLeafId = null
            throw CancellationException("Отмена невозможна - запрос (какого-то черта) не найден")
        }
    }
}