package requests

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class RemoveByIdRequest(private val id: Long) : Request {
    override fun process(collection: LinkedList<Organization>): String {
        if (collection.remove(collection.find { it.id == id }))
            return EventMessage.message("Элемент удален", TextColor.BLUE)

        return EventMessage.message("Элемент с id $id не найден", TextColor.RED)
    }
}