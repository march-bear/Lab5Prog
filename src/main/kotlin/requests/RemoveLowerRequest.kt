package requests

import Organization
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class RemoveLowerRequest(private val element: Organization) : Request {
    override fun process(collection: LinkedList<Organization>): String {
        var i = 0
        var output = ""
        while (i < collection.size) {
            if (collection[i] < element) {
                output += EventMessage.message(
                    "Удален элемент с id ${collection.removeAt(i).id}\n",
                    TextColor.BLUE
                )
                continue
            }
            i++
        }

        if (output != "")
            return output

        return EventMessage.message("В коллекции нет элементов, меньших, чем введенный", TextColor.BLUE)
    }
}