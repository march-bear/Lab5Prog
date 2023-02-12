package commands

import Address
import Coordinates
import EventMessage
import Organization
import OrganizationType
import exceptions.InvalidFieldValueException
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
            try {
                EventMessage.inputPrompt("Имя организации")
                name = Reader.readOrganizationName()
                break
            } catch (e: InvalidFieldValueException) {
                EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
            }
        }

        EventMessage.greenMessageln("Координаты")
        while (true) {
            try {
                EventMessage.inputPrompt("\tx")
                x = Reader.readCoordinateX()
                break
            } catch (e: InvalidFieldValueException) {
                EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
            }
        }

        while (true) {
            try {
                EventMessage.inputPrompt("\ty")
                y = Reader.readCoordinateY()
                break
            } catch (e: InvalidFieldValueException) {
                EventMessage.redMessageln("\tНевалидное значение поля. Повторите ввод")
            }
        }

        while (true) {
            try {
                EventMessage.inputPrompt("Годовой оборот")
                annualTurnover = Reader.readAnnualTurnover()
                break
            } catch (e: InvalidFieldValueException) {
                EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
            }
        }

        while (true) {
            EventMessage.inputPrompt("Полное имя (при наличии)")
            fullName = Reader.readStringOrNull()
            if (Organization.fullNameIsValid(fullName))
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Количество сотрудников (при наличии)")
            val employeesCountString = Reader.readStringOrNull()
            employeesCount = try {
                employeesCountString?.toLong()
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
                Reader.readString().let { OrganizationType.valueOf(it) }
            } catch (e: IllegalArgumentException) {
                null
            }
            if (type != null)
                break
            EventMessage.redMessageln("Невалидное значение поля. Повторите ввод")
        }

        while (true) {
            EventMessage.inputPrompt("Почтовый адрес (ZIP-код, при наличии)")
            zipCode = Reader.readStringOrNull()
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