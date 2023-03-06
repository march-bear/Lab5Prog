package requests

import Organization
import collection.CollectionWrapper


/**
 * Интерфейс, реализуемый всеми запросами
 */
interface Request {
    /**
     * Обрабатывает запрос
     * @param collection коллекция, по отношению к которой обрабатывается запрос
     */
    fun process(collection: CollectionWrapper<Organization>): String

    /**
     * Отменяет запрос
     */
    fun cancel(): String
}