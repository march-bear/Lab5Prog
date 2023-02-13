package commands

import Address
import Coordinates
import iostreamers.EventMessage
import Organization
import OrganizationType
import exceptions.InvalidArgumentsForCommandException
import exceptions.InvalidFieldValueException
import iostreamers.Reader
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.util.*

class AddCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Аргументы передаются на следующих строках")

        val name: String? = Reader.readField("Имя организации",
            {Reader.readOrganizationName()},)


        EventMessage.messageln("Координаты", TextColor.GREEN)

        val x: Double? = Reader.readField("\tx",
            {Reader.readCoordinateX()},
            "\tНевалидное значение поля. Повторите ввод")

        val y: Int? = Reader.readField("\ty",
            {Reader.readCoordinateY()},
            "\tНевалидное значение поля. Повторите ввод")

        val annualTurnover: Int? = Reader.readField("Годовой оборот",
            {Reader.readAnnualTurnover()})

        val fullName: String? = Reader.readField("Полное имя (при наличии)",
            {Reader.readFullName(collection)})

        val employeesCount: Long? = Reader.readField("Количество сотрудников (при наличии)",
            {Reader.readEmployeesCount()})

        val type: OrganizationType? = Reader.readField("Тип " +
                "(COMMERCIAL/GOVERNMENT/TRUST/PRIVATE_LIMITED_COMPANY/OPEN_JOINT_STOCK_COMPANY)",
            {Reader.readOrganizationType()})

        val postalAddress: Address? = Reader.readField("Почтовый адрес (ZIP-код, при наличии)",
            {Reader.readAddress()})


        println()

        val newElement = Organization(name, Coordinates(x, y), annualTurnover,
            fullName, employeesCount, type, postalAddress)
        newElement.setId(Generator.generateUniqueId(collection))
        collection.add(newElement)
    }


    override fun getInfo(): String =
        "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"
}