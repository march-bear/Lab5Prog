package commands

class SumOfEmployeesCountCommand : Command {
    override fun execute(args: String?) {
        TODO("Not yet implemented")
    }

    override fun getInfo(): String =
        "вывести сумму значений поля employeesCount для всех элементов коллекции"
}