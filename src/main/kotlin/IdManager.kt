import java.util.*


class IdManager(private val collection: LinkedList<Organization>) {
    private var counter: Long = 1
    private val freeIdLessCounter = LinkedList<Long>()

    init {
        updateGenerator()
    }

    fun generateId(): Long? {
        if (collection.size == Int.MAX_VALUE) {
            return null
        }

        if (freeIdLessCounter.isNotEmpty())
            return freeIdLessCounter.pop()

        if (counter < 1) {
            updateGenerator()
        }

        return counter++
    }

    fun updateAllId() {
        counter = 1
        collection.forEach { it.id = counter++ }
        freeIdLessCounter.clear()
    }

    private fun updateGenerator() {
        counter = if (collection.isEmpty()) 1 else collection.maxBy { it.id }.id + 1
        freeIdLessCounter.clear()
        collection.sortBy { it.id }
        var currId: Long = 1
        var collectionCounter = 0
        while (
            collectionCounter < Int.MAX_VALUE
            && freeIdLessCounter.size < MAX_SIZE_FREE_ID_LESS_COUNTER
            && collectionCounter < collection.size
        )
            if (collection[collectionCounter].id != currId) {
                freeIdLessCounter.add(currId++)
            } else {
                collectionCounter++
                currId++
            }
    }

    fun checkIdInCollection(): Boolean {
        val uniqueId = mutableSetOf<Long>()
        collection.forEach { uniqueId.add(it.id) }
        return uniqueId.size != collection.size
    }

    companion object {
        private const val MAX_SIZE_FREE_ID_LESS_COUNTER = 1000
    }
}

/*
    fun makeIdFree(id: Long) {
        if (!Organization.idIsValid(id))
            throw IllegalArgumentException("Невозможный id")

        if (collection.find { it.id == id } == null)
            throw IllegalArgumentException("Id свободен")

        if (counter - id == 1L) {
            counter--
        } else if (counter > id) {
            if (freeIdLessCounter.size <= MAX_SIZE_FREE_ID_LESS_COUNTER)
                freeIdLessCounter.add(id)
        } else
            throw IdException()

    }
 */