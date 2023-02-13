import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.lang.Exception
import java.lang.System.currentTimeMillis
import java.util.Date
import kotlin.math.max

@Serializable
class Organization : Comparator<Organization> {
    companion object {
        fun idIsValid(id: Long?): Boolean = id != null && id > 0
        fun nameIsValid(name: String?): Boolean = name != "" && name != null
        fun coordinatesIsValid(coordinates: Coordinates?): Boolean = coordinates != null
        fun annualTurnoverIsValid(annualTurnover: Int?): Boolean = annualTurnover != null && annualTurnover > 0
        fun fullNameIsValid(fullName: String?): Boolean = fullName == null || fullName.length in 0..925
        fun employeesCountIsValid(employeesCount: Long?): Boolean = employeesCount == null || employeesCount > 0
        fun typeIsValid(type: OrganizationType?): Boolean = type != null
        class DateAsLongSerializer : KSerializer<Date> {
            override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)

            override fun deserialize(decoder: Decoder): Date {
                val string = decoder.decodeLong()
                return Date(string)
            }

            override fun serialize(encoder: Encoder, value: Date) {
                val string = value.time
                encoder.encodeLong(string)
            }

        }
    }

    private var id: Long? = -1 // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
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

    @Serializable(with = DateAsLongSerializer::class)
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

    fun setId(id: Long?) {
        this.id = id
    }

    fun getId(): Long? = this.id
    fun getFullName(): String? = this.fullName
    constructor(name: String?, coordinates: Coordinates, annualTurnover: Int?, fullName: String?,
                employeesCount: Long?, type: OrganizationType?, postalAddress: Address?) {
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
        return "название организации: \u001B[34m$name\u001B[39m\n" +
                "id: \u001B[34m$id\u001B[39m\n" +
                "координаты: \u001B[34m${coordinates.toString()}\u001B[39m\n" +
                "дата создания: \u001B[34m$creationDate\u001B[39m\n" +
                "годовой оборот: \u001B[34m$annualTurnover\u001B[39m\n" +
                "полное имя: \u001B[34m$fullName\u001B[39m\n" +
                "количество сотрудников: \u001B[34m$employeesCount\u001B[39m\n" +
                "тип: \u001B[34m$type\u001B[39m\n" +
                "почтовый адрес: \u001B[34m$postalAddress\u001B[39m"
    }

    override fun compare(o1: Organization?, o2: Organization?): Int {
        if (o1 == null)
            return if (o2 == null) 0 else -1
        if (o2 == null)
            return 1

        if (o1.annualTurnover != o2.annualTurnover) {
            if (o1.annualTurnover == null)
                return 1
            if (o2.annualTurnover == null)
                return -1
            return if (o1.annualTurnover!! < o2.annualTurnover!!) 1 else -1
        }

        if (o1.employeesCount != o2.employeesCount) {
            if (o1.employeesCount == null)
                return 1
            if (o2.employeesCount == null)
                return -1
            return if (o2.employeesCount!! < o1.employeesCount!!) 1 else -1
        }

        return 0
    }
}

@Serializable
class Coordinates {

    constructor(x: Double?, y: Int?) {
        this.x = x
        this.y = y
    }

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

    override fun toString(): String {
        return "$x $y"
    }
}

@Serializable
class Address {
    constructor(zipCode: String?) {
        this.zipCode = zipCode
    }

    companion object {
        fun zipCodeIsValid(zipCode: String?): Boolean = zipCode != null
    }

    private var zipCode: String? = null //Поле не может быть null
        set(zipCode: String?) {
            if (zipCode == null)
                throw Exception()
            field = zipCode
        }

    override fun toString(): String {
        return zipCode.toString()
    }
}

@Serializable
enum class OrganizationType {
    COMMERCIAL, GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY, OPEN_JOINT_STOCK_COMPANY
}