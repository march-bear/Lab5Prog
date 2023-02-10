import java.lang.Exception
import java.lang.System.currentTimeMillis
import java.util.Date

class Organization  {
    private companion object {
        var idCounter: Long = 1
    }
    private var id: Long = idCounter++ // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private var name: String? // Поле не может быть null, Строка не может быть пустой
        set(name: String?) {
            when (name) {
                null -> throw Exception()
                "" -> throw Exception()
                else -> field = name
            }
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
        type: OrganizationType,
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
}

class Coordinates {
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

class Address {
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