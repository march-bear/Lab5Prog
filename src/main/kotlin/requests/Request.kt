package requests

import Organization
import java.util.*


/**
 * Интерфейс, реализуемый всеми запросами
 */
interface Request {
    /**
     * Обрабатывает запрос
     * @param collection коллекция, по отношению к которой обрабатывается запрос
     */
    fun process(collection: LinkedList<Organization>): String

    /**
     * Отменяет запрос
     */
    fun cancel(): String
}