package requests

import Organization
import commands.CommandData
import java.util.*

class ExecuteCommandsRequest(private val commands: List<CommandData>) : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {

    }
}