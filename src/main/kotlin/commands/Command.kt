package commands

interface Command {
    fun execute(args: String?)
    fun getInfo(): String
}