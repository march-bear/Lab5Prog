package commands

class CommandNotFountException(message: String = "Команда не найдена") :
    RuntimeException(message)