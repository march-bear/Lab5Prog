package commands

class SumOfEmployeesCountCommand : Command {
    override fun execute(s: String) {
        TODO("Not yet implemented")
    }

    override fun getInfo(): String =
        "вывести сумму значений поля employeesCount для всех элементов коллекции"
}