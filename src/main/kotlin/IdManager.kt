import exceptions.IdException
import java.util.*


class IdManager(private val collection: LinkedList<Organization>) {
    private var counter: Long = 1
    private val freeIdLessCounter = LinkedList<Long>()
    private var thereAreFreeId = true

    init {
        updateGenerator()
    }

    fun generateId(): Long? {
        if (!thereAreFreeId)
            return null
        if (collection.size == Int.MAX_VALUE) {
            thereAreFreeId = false
            return null
        }

        if (counter < 1) {
            updateGenerator()
        }

        if (freeIdLessCounter.isNotEmpty())
            return freeIdLessCounter.pop()

        return counter++
    }

    fun makeIdFree(id: Long) {
        if (!Organization.idIsValid(id))
            throw IllegalArgumentException("Невозможный id")

        if(collection.find { it.getId() == id} == null)
            throw IllegalArgumentException("Id свободен")

        if (counter - id == 1L) {
            counter--
        } else if (counter > id) {
            if (freeIdLessCounter.size <= MAX_SIZE_FREE_ID_LESS_COUNTER)
                freeIdLessCounter.add(id)
        } else
            throw IdException()

    }

    fun updateAllId() {
        counter = 1
        collection.forEach { it.setId(counter++) }
        freeIdLessCounter.clear()
    }

    private fun updateGenerator() {
        counter = collection.maxBy { it.getId() }.getId() + 1
        collection.sortBy { it.getId() }
        var currId: Long = 1
        var collectionCounter = 0
        while (collectionCounter < Int.MAX_VALUE && freeIdLessCounter.size < MAX_SIZE_FREE_ID_LESS_COUNTER)
            if (collection[collectionCounter].getId() != currId) {
                freeIdLessCounter.add(currId++)
            } else {
                collectionCounter++
                currId++
            }
    }

    fun checkIdInCollection(): Boolean {
        val uniqueId = mutableSetOf<Long>()
        collection.forEach { uniqueId.add(it.getId()) }
        return uniqueId.size != collection.size
    }

    companion object {
        private const val MAX_SIZE_FREE_ID_LESS_COUNTER = 1000
    }
}