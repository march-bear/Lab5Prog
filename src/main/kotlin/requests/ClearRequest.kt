package requests

import Organization
import java.util.*

class ClearRequest : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {
        collection.clear()
        return true
    }
}