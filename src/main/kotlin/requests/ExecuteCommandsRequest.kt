package requests

import CollectionController
import Organization
import command.CommandData
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class ExecuteCommandsRequest(
    private val commands: List<CommandData>,
    private val controller: CollectionController,
) : Request {
    override fun process(collection: LinkedList<Organization>): String {
        commands.forEach {
            controller.execute(it)
        }
        return EventMessage.message("Скрипт выполнен", TextColor.BLUE)
    }
}