package requests

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class ClearRequest : Request {
    override fun process(collection: LinkedList<Organization>): String {
        collection.clear()
        return EventMessage.message("Коллекция очищена", TextColor.BLUE)
    }
}