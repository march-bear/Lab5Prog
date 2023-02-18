package requests

import Organization
import java.util.*

class RemoveByIdRequest(private val id: Long) : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {
        collection.remove(collection.find { it.getId() == id })
        return true
    }

    override fun toString(): String {
        return "удалить элемент по id $id"
    }
}