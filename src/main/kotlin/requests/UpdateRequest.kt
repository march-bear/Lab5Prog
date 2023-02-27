package requests

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class UpdateRequest(private val id: Long, private val element: Organization) : Request {
    override fun process(collection: LinkedList<Organization>): String {
        try {
            collection[collection.indexOf(collection.find { it.id == id })] = element
        } catch (e: IndexOutOfBoundsException) {
            return EventMessage.message("Элемент с id $id не найден", TextColor.RED)
        }

        return "Значение элемента с id $id обновлено"
    }
}