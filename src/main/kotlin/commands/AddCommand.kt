package commands

import Address
import Coordinates
import EventMessage
import Organization
import OrganizationType
import java.util.*

class AddCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(s: String) {
        var name: String?
        var x: Double?
        var y: Int?
        var annualTurnover: Int?
        var fullName: String?
        var employeesCount: Long?
        var type: OrganizationType?
        var zipCode: String?

        while (true) {
            EventMessage.inputPrompt("Имя организации")
            name = readLine()
            if (Organization.nameIsValid(name))
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }

        EventMessage.greenMessageln("Координаты")
        while (true) {
            EventMessage.inputPrompt("\tx")
            x = readLine()?.toDoubleOrNull()
            if (Coordinates.xIsValid(x))
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("\ty")
            y = readLine()?.toIntOrNull()
            if (Coordinates.yIsValid(y))
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Годовой оборот")
            annualTurnover = readLine()?.toIntOrNull()
            if (Organization.annualTurnoverIsValid(annualTurnover))
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Полное имя (при наличии)")
            fullName = readLine()
            if (fullName == null || Organization.fullNameIsValid(fullName))
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Количество сотрудников (при наличии)")
            employeesCount = readLine()?.toLongOrNull()
            if (employeesCount == null || Organization.employeesCountIsValid(employeesCount))
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Тип " +
                    "(COMMERCIAL/GOVERNMENT/TRUST/PRIVATE_LIMITED_COMPANY/OPEN_JOINT_STOCK_COMPANY)")
            type = readLine()?.let { OrganizationType.valueOf(it) }
            if (type != null)
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Почтовый адрес (ZIP-код, при наличии)")
            zipCode = readLine()
            if (zipCode == null || Address.zipCodeIsValid(zipCode))
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }
        val postalAddress: Address? = if (zipCode != null) Address(zipCode) else null

        collection.add(Organization(name, Coordinates(x, y), annualTurnover,
            fullName, employeesCount, type, postalAddress))
    }


    override fun getInfo(): String =
        "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"
}