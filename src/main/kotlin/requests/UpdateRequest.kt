package requests

import Organization
import java.util.*

class UpdateRequest(private val id: Long, private val element: Organization) : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {
        collection[collection.indexOf(collection.find { it.getId() == id })] = element
        return true
    }
}