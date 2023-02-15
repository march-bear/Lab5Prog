import java.util.*

/**
 * Служит для генерации некоторых значений
 */
class Generator private constructor() {
    companion object {
        /**
         * Генерирует уникальный для переданной коллекции id элемента
         */
        fun generateUniqueId(collection: LinkedList<Organization>): Long {
            val r = Random()
            while (true) {
                var id = r.nextLong()
                if (!Organization.idIsValid(id))
                    continue
                var counter = 0
                for (elem in collection) {
                    if (id == elem.getId())
                        break
                    counter++
                }
                if (counter == collection.size)
                    return id
            }
        }
    }
}