package exceptions

class CyclicScriptCallException(
    override val message: String? = "Обнаружен циклический вызов скрипта"
) : Exception(message)