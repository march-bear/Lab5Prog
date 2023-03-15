package requests

import Organization
import collection.CollectionWrapper
import commandcallgraph.RequestGraph

class RollbackRequest(private val requestGraph: RequestGraph, private val id: String): Request {
    override fun process(collection: CollectionWrapper<Organization>): Response {
        requestGraph.rollback(id)
        return Response(true, "Всё гуд, отвечаю", false)
    }

    override fun cancel(): String {
        return "Отменил, отвечаю"
    }
}