package requests

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class AddRequest(private val elem: Organization) : Request {
    override fun process(collection: LinkedList<Organization>): String {
        collection.add(elem)
        return EventMessage.message("Элемент добавлен в коллекцию", TextColor.BLUE)
    }
}