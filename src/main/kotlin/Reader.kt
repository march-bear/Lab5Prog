import exceptions.InvalidFieldValueException
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

class Reader {
    companion object {

        fun readStringOrNull(): String? {
            var input = readln()
            input = input.trim()
            return if (input != "") input else null
        }

        fun readString(): String {
            return readln().trim()
        }

        fun readOrganizationName(): String? {
            val name: String? = readStringOrNull()
            if (Organization.nameIsValid(name))
                return name
            throw InvalidFieldValueException()
        }

        fun readCoordinateX(): Double? {
            val x: Double? = readStringOrNull()?.toDoubleOrNull()
            if (Coordinates.xIsValid(x))
                return x
            throw InvalidFieldValueException()
        }

        fun readCoordinateY(): Int? {
            val y: Int? = readStringOrNull()?.toIntOrNull()
            if (Coordinates.yIsValid(y))
                return y
            throw InvalidFieldValueException()
        }

        fun readAnnualTurnover(): Int? {
            val annualTurnover: Int? = readStringOrNull()?.toIntOrNull()
            if (Organization.annualTurnoverIsValid(annualTurnover))
                return annualTurnover
            throw InvalidFieldValueException()
        }

        fun readFullName(): String? {
            val fullName: String? = readStringOrNull()
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
            if (Organization.typeIsValid(type))
                return type
            throw InvalidFieldValueException()
        }

        fun readAddress(): Address? {
            val zipCode = readStringOrNull()
            if (Address.zipCodeIsValid(zipCode))
                return Address(zipCode)
            if (zipCode == null)
                return null
            throw InvalidFieldValueException()
        }


    }
}