package requests

import Organization
import java.util.*

class RemoveHeadRequest : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {
        collection.removeFirst()
        return true
    }
}