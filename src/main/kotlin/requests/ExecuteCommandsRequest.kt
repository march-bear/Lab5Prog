package requests

import CollectionController
import Organization
import commands.CommandData
import java.util.*

class ExecuteCommandsRequest(
    private val commands: List<CommandData>,
    private val controller: CollectionController,
) : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {
        commands.forEach {
            controller.execute(it)
        }
        return true
    }
}