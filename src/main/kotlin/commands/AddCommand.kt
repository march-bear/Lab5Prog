package commands

import Address
import Coordinates
import EventMessage
import Organization
import OrganizationType
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.lang.NumberFormatException
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
            name = readln()
            if (Organization.nameIsValid(name))
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }

        EventMessage.greenMessageln("Координаты")
        while (true) {
            EventMessage.inputPrompt("\tx")
            x = readln().toDoubleOrNull()
            if (Coordinates.xIsValid(x))
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("\ty")
            y = readln().toIntOrNull()
            if (Coordinates.yIsValid(y))
                break
            EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Годовой оборот")
            annualTurnover = readln().toIntOrNull()
            if (Organization.annualTurnoverIsValid(annualTurnover))
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Полное имя (при наличии)")
            fullName = readln()
            if (fullName == null || Organization.fullNameIsValid(fullName))
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Количество сотрудников (при наличии)")
            val employeesCountString = readln()
            employeesCount = if (employeesCountString == "") null else try {
                employeesCountString.toLong()
            } catch (e: NumberFormatException) {
                -1
            }
            if (Organization.employeesCountIsValid(employeesCount))
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Тип " +
                    "(COMMERCIAL/GOVERNMENT/TRUST/PRIVATE_LIMITED_COMPANY/OPEN_JOINT_STOCK_COMPANY)")
            type = try {
                readln().let { OrganizationType.valueOf(it) }
            } catch (e: IllegalArgumentException) {
                null
            }
            if (type != null)
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Почтовый адрес (ZIP-код, при наличии)")
            zipCode = readln()
            if (Address.zipCodeIsValid(zipCode))
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }
        println()

        val postalAddress: Address? = if (zipCode != null) Address(zipCode) else null

        collection.add(Organization(name, Coordinates(x, y), annualTurnover,
            fullName, employeesCount, type, postalAddress))
    }


    override fun getInfo(): String =
        "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"
}