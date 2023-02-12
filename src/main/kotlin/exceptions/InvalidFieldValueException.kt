package exceptions

class InvalidFieldValueException(override val message: String? = "Невалидное значение поля"): RuntimeException(message)