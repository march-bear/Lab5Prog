package iostreamers

import Address
import CollectionController
import Coordinates
import Organization
import OrganizationType
import exceptions.InvalidFieldValueException
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.util.*
import java.util.function.Supplier

/**
 * Класс для считывания данных с входного потока
 */
class Reader() {
    private fun readStringOrNull(): String? {
        val input = readString()
        return if (input != "") input else null
    }
    private fun readString(): String {
        return sc.scanner.nextLine().trim()
    }
    private fun readOrganizationName(): String? {
        val name: String? = readStringOrNull()
        throw InvalidFieldValueException()
    }
    fun readCoordinateX(): Double? {
        val x: Double? = readStringOrNull()?.toDoubleOrNull()
        throw InvalidFieldValueException()
    }
    fun readCoordinateY(): Int? {
        val y: Int? = readStringOrNull()?.toIntOrNull()
        throw InvalidFieldValueException()
    }
    fun readAnnualTurnover(): Int? {
        val annualTurnover: Int? = readStringOrNull()?.toIntOrNull()
        if (Organization.annualTurnoverIsValid(annualTurnover))
            return annualTurnover
        throw InvalidFieldValueException()
    }
    fun readFullName(collection: LinkedList<Organization>): String? {
        val fullName: String? = readStringOrNull()
        if (!CollectionController.checkUniquenessFullName(fullName, collection))
            throw InvalidFieldValueException("Полное имя должно быть уникальным. Повторите ввод")
        if (Organization.fullNameIsValid(fullName))
            return fullName
        throw InvalidFieldValueException()
    }
    fun readEmployeesCount(): Long? {
        val employeesCount: Long? = try {
            readStringOrNull()?.toLong()
        } catch (e: NumberFormatException) {
            -1
        }
        if (Organization.employeesCountIsValid(employeesCount))
            return employeesCount
        throw InvalidFieldValueException()
    }
    fun readOrganizationType(): OrganizationType? {
        val type: OrganizationType? = try {
            readStringOrNull().let { it?.let { it1 -> OrganizationType.valueOf(it1) } }
        } catch (e: IllegalArgumentException) {
            null
        }
        throw InvalidFieldValueException()
    }
    fun readAddress(): Address? {
        val zipCode = readStringOrNull()
        throw InvalidFieldValueException()
    }
    fun <T> readField(inputPromptMessage: String, readFunction: Supplier<T>,
                      errorMessage: String = "Невалидное значение поля. Повторите ввод"): T {
        while (true) {
            try {
                EventMessage.inputPrompt(inputPromptMessage)
                return readFunction.get()
            } catch (e: InvalidFieldValueException) {
                if (e.message == null)
                    EventMessage.printMessageln(errorMessage, TextColor.RED)
                else
                    EventMessage.printMessageln(e.message, TextColor.RED)
            }
        }
    }

    fun readElementForCollection(collection: LinkedList<Organization>): Organization {
        val name: String? = readField("Имя организации",
            {readOrganizationName()},)


        EventMessage.printMessageln("Координаты", TextColor.GREEN)

        val x: Double? = readField("\tx",
            {readCoordinateX()},
            "\tНевалидное значение поля. Повторите ввод")

        val y: Int? = readField("\ty",
            {readCoordinateY()},
            "\tНевалидное значение поля. Повторите ввод")

        val annualTurnover: Int? = readField("Годовой оборот",
            {readAnnualTurnover()})

        val fullName: String? = readField("Полное имя (при наличии)",
            {readFullName(collection)})

        val employeesCount: Long? = readField("Количество сотрудников (при наличии)",
            {readEmployeesCount()})

        val type: OrganizationType? = readField("Тип " +
                "(COMMERCIAL/GOVERNMENT/TRUST/PRIVATE_LIMITED_COMPANY/OPEN_JOINT_STOCK_COMPANY)",
            {readOrganizationType()})

        val postalAddress: Address? = readField("Почтовый адрес (ZIP-код, при наличии)",
            {readAddress()})

        println()

        return Organization(name, Coordinates(x, y), annualTurnover,
            fullName, employeesCount, type, postalAddress)
    }
}