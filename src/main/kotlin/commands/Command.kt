package commands

interface Command {
    fun execute(s: String)
    fun getInfo(): String
}