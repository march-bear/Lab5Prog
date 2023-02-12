package commands

import exceptions.ExitCommandCall

class ExitCommand : Command {
    override fun execute(s: String) {
        throw ExitCommandCall()
    }

    override fun getInfo(): String = "завершить программу (без сохранения в файл)"
}