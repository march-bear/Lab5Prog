package exceptions

class IdException(override val message: String? = "Сбой в механизме идентификации объектов") : Exception(message)