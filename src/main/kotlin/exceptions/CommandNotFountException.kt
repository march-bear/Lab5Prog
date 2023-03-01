package exceptions

class CommandNotFountException(
    val commandName: String,
    override val message: String? = "$commandName: команда не найдена",
) : Exception(message)