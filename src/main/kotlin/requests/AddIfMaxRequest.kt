package requests

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class AddIfMaxRequest(private val element: Organization) : Request {
    override fun process(collection: LinkedList<Organization>): String {
        if (element > collection.max()) {
            collection.add(element)
            return EventMessage.message("Элемент добавлен в коллекцию", TextColor.BLUE)
        }
        return EventMessage.message("Элемент не является максимальным", TextColor.BLUE)
    }
}