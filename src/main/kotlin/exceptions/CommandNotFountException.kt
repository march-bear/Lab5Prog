package exceptions

class CommandNotFountException(override val message: String? = "Команда не найдена") : RuntimeException(message)