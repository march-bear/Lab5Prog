package requests

import CollectionController
import Organization
import command.Command
import command.CommandArgument
import iostreamers.EventMessage
import iostreamers.TextColor
import java.util.*

class ExecuteCommandsRequest(
    private val commands: List<Pair<Command, CommandArgument>>,
    private val controller: CollectionController,
    private val collection: LinkedList<Organization>,
) : Request {
    override fun process(collection: LinkedList<Organization>): String {
        val copy = LinkedList<Organization>()
        copy.addAll(collection)
        for (i in commands.indices) {

        }

        collection.clear()
        collection.addAll(copy)
        return EventMessage.message("Скрипт выполнен", TextColor.BLUE)
    }
}