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

class AddCommand(private val collection: LinkedList<Organization>, private val reader: Reader) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Аргументы передаются на следующих строках")

        val name: String? = reader.readField("Имя организации",
            {reader.readOrganizationName()},)


        EventMessage.messageln("Координаты", TextColor.GREEN)

        val x: Double? = reader.readField("\tx",
            {reader.readCoordinateX()},
            "\tНевалидное значение поля. Повторите ввод")

        val y: Int? = reader.readField("\ty",
            {reader.readCoordinateY()},
            "\tНевалидное значение поля. Повторите ввод")

        val annualTurnover: Int? = reader.readField("Годовой оборот",
            {reader.readAnnualTurnover()})

        val fullName: String? = reader.readField("Полное имя (при наличии)",
            {reader.readFullName(collection)})

        val employeesCount: Long? = reader.readField("Количество сотрудников (при наличии)",
            {reader.readEmployeesCount()})

        val type: OrganizationType? = reader.readField("Тип " +
                "(COMMERCIAL/GOVERNMENT/TRUST/PRIVATE_LIMITED_COMPANY/OPEN_JOINT_STOCK_COMPANY)",
            {reader.readOrganizationType()})

        val postalAddress: Address? = reader.readField("Почтовый адрес (ZIP-код, при наличии)",
            {reader.readAddress()})


        println()

        val newElement = Organization(name, Coordinates(x, y), annualTurnover,
            fullName, employeesCount, type, postalAddress)
        newElement.setId(Generator.generateUniqueId(collection))
        collection.add(newElement)
    }


    override fun getInfo(): String =
        "добавить новый элемент в коллекцию (поля элемента указываются на отдельных строках)"
}