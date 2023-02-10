package commands

interface Command {
    fun execute()
    fun getInfo(): String
}