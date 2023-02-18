package requests

import Organization
import java.util.*

class AddRequest(private val elem: Organization) : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {
        collection.add(elem)
        return true
    }
}