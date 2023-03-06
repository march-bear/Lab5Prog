package commandcallgraph

import command.Command
import requests.Request
import java.util.*
import kotlin.collections.HashMap

class Leaf(
    command: Command,
    val request: Request? = null,
    val previousLeaf: Leaf? = null,
) {
    val id: String
    private val nextLeafs = LinkedList<Leaf>()

    init {
        val commandClassName = command::class.simpleName.toString().uppercase()
        id = "${commandClassName}_${getNumber(commandClassName)}"
    }

    fun addNextLeaf(command: Command, request: Request? = null, previousLeaf: Leaf? = null) {
        nextLeafs.add(Leaf(command, request, previousLeaf))
    }

    fun getNextLeafs(): List<Leaf> {
        return LinkedList(nextLeafs)
    }

    private companion object {
        val countNumbers: HashMap<String, Long> = HashMap()
        fun getNumber(commandName: String): Long {
            if (countNumbers[commandName] == null)
                countNumbers[commandName] = 1L
            else
                countNumbers[commandName] = countNumbers[commandName]!! + 1L

            return countNumbers[commandName]!!
        }
    }
}