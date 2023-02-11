import java.lang.Exception
import java.lang.System.currentTimeMillis
import java.util.Date

class Organization  {
    companion object {
        private var idCounter: Long = 1
        fun nameIsValid(name: String?): Boolean = name != "" && name != null
        fun coordinatesIsValid(coordinates: Coordinates?): Boolean = coordinates != null
        fun annualTurnoverIsValid(annualTurnover: Int?): Boolean = annualTurnover != null && annualTurnover > 0
        fun fullNameIsValid(fullName: String?): Boolean = fullName == null || fullName.length in 0..925
        fun employeesCountIsValid(employeesCount: Long?): Boolean = employeesCount == null || employeesCount > 0
        fun typeIsValid(type: OrganizationType?): Boolean = type != null

    }

    private var id: Long = idCounter++ // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private var name: String? // Поле не может быть null, Строка не может быть пустой
        set(name) {
            if (nameIsValid(name))
                field = name
            else
                throw Exception()
        }
    private var coordinates: Coordinates? // Поле не может быть null
        set(coordinates: Coordinates?) {
            when (coordinates) {
                null -> throw Exception()
                else -> field = coordinates
            }
        }
    private var creationDate: Date? // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private var annualTurnover: Int? // Поле не может быть null, Значение поля должно быть больше 0
        set(annualTurnover: Int?) {
            if (annualTurnover == null || annualTurnover <= 0)
                throw Exception()
            field = annualTurnover
        }
    private var fullName: String? // Длина строки не должна быть больше 925, Значение этого поля должно быть уникальным, Поле может быть null
        set(fullName: String?) {
            if (fullName == null || fullName.length in 0..925)
                field = fullName
            else
                throw Exception()
        }
    private var employeesCount: Long? // Поле может быть null, Значение поля должно быть больше 0
        set(employeesCount: Long?) {
            if (employeesCount == null || employeesCount > 0)
                field = employeesCount
            else
                throw Exception()
        }
    private var type: OrganizationType? // Поле не может быть null
        set(type: OrganizationType?) {
            when (type) {
                null -> throw Exception()
                else -> field = type
            }
        }
    private var postalAddress: Address? // Поле может быть null

    constructor(
        name: String?,
        coordinates: Coordinates,
        annualTurnover: Int?,
        fullName: String?,
        employeesCount: Long?,
        type: OrganizationType?,
        postalAddress: Address?,
    ) {
        this.name = name
        this.coordinates = coordinates
        this.creationDate = Date((Math.random() * currentTimeMillis()).toLong())
        this.annualTurnover = annualTurnover
        this.fullName = fullName
        this.employeesCount = employeesCount
        this.type = type
        this.postalAddress = postalAddress
    }

    override fun toString(): String {
        return "Организация $name:\n" +
                "id=$id,\n" +
                "coordinates=$coordinates,\n" +
                "creationDate=$creationDate,\n" +
                "annualTurnover=$annualTurnover,\n" +
                "fullName=$fullName,\n" +
                "employeesCount=$employeesCount,\n" +
                "type=$type,\n" +
                "postalAddress=$postalAddress)"
    }
}

class Coordinates(x: Double?, y: Int?) {
    companion object {
        fun xIsValid(x: Double?): Boolean = x != null
        fun yIsValid(y: Int?): Boolean = y != null
    }
    private var x: Double? = null // Поле не может быть null
        set(x: Double?) {
            if (x == null)
                throw Exception()
            field = x
        }
    private var y: Int? = null // Поле не может быть null
        set(y: Int?) {
            if (y == null)
                throw Exception()
            field = y
        }
}

class Address(zipCode: String?) {
    companion object {
        fun zipCodeIsValid(zipCode: String?): Boolean = zipCode != null
    }

    private var zipCode: String? = null //Поле не может быть null
        set(zipCode: String?) {
            if (zipCode == null)
                throw Exception()
            field = zipCode
        }
}

enum class OrganizationType {
    COMMERCIAL, GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY, OPEN_JOINT_STOCK_COMPANY
}