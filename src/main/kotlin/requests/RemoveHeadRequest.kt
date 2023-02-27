package requests

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class RemoveHeadRequest : Request {
    override fun process(collection: LinkedList<Organization>): String {
        if (collection.isEmpty())
            return EventMessage.message("Элемент не может быть удален - коллекция пуста", TextColor.RED)

        return collection.removeFirst().toString() + EventMessage.message("Элемент удален", TextColor.BLUE)
    }
}