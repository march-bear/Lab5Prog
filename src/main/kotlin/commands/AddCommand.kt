package commands

import EventMessage
import Organization
import java.util.LinkedList

class AddCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute() {
        EventMessage.inputPrompt("Имя организации")
        readln()
        EventMessage.inputPrompt("Координаты\n\tx")
        readln()
        EventMessage.inputPrompt("\ty")
        readln()
        EventMessage.inputPrompt("Годовой оборот")
        readln()
        EventMessage.inputPrompt("Полное имя (при наличии)")
        readln()
        EventMessage.inputPrompt("Количество сотрудников")
        readln()
        EventMessage.inputPrompt("Тип")
        readln()
        EventMessage.inputPrompt("Почтовый адрес")
        readln()
    }

    override fun getInfo(): String =
        "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"
}