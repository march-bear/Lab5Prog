package exceptions

class CommandRegistrationException(
    override val message: String? = "Ошибка о время регистрации команды"
) : Exception(message)