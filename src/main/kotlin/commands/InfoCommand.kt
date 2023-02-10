package commands


class InfoCommand : Command {
    override fun execute() {

    }

    override fun getInfo(): String =
        "вывести в стандартный поток вывода информацию о коллекции"
}