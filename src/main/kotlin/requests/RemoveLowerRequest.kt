package requests

import Organization
import java.util.*

class RemoveLowerRequest(private val element: Organization) : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {
        var i = 0
        while (i < collection.size) {
            if (collection[i] < element) {
                collection.removeAt(i)
                continue
            }
            i++
        }
        return true
    }
}