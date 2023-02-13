package exceptions

class InvalidFieldValueException(override val message: String? = null): RuntimeException(message)