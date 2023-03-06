package collection

import kotlinx.serialization.Serializable
import java.util.*

class QueueWrapper<E>(private val queue: Queue<E> = LinkedList()): CollectionWrapperInterface<E> {
    override val size: Int
        get() = queue.size

    override fun add(element: E): Boolean = queue.add(element)

    override fun replace(curr: E, new: E) {
        queue.remove(curr) && queue.add(new)
    }

    override fun replaceBy(new: E, predicate: (E) -> Boolean) {
        queue.remove(queue.find(predicate)) && queue.add(new)
    }

    override fun isEmpty(): Boolean = queue.isEmpty()

    override fun clear() = queue.clear()

    override fun remove(): E = queue.remove()

    override fun remove(element: E): Boolean = queue.remove(element)

    override fun iterator(): Iterator<E> = queue.iterator()

    override fun getCollectionType(): String = "Queue"
}