package requests

import Organization
import java.util.*

class AddIfMaxRequest(private val element: Organization) : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {
        if (element > collection.max()) {
            collection.add(element)
        }
        return false
    }
}