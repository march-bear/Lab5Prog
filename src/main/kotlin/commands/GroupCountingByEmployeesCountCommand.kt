package commands

class GroupCountingByEmployeesCountCommand : Command {
    override fun execute(args: String?) {
        TODO("Not yet implemented")
    }

    override fun getInfo(): String =
        "сгруппировать элементы коллекции по значению поля employeesCount, вывести количество элементов в каждой группе"
}