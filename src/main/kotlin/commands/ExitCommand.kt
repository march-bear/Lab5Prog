package commands

import exceptions.ExitCommandCall

class ExitCommand : Command {
    override fun execute(args: String?) {
        throw ExitCommandCall()
    }

    override fun getInfo(): String = "завершить программу (без сохранения в файл)"
}