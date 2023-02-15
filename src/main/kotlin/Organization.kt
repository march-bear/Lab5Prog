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
class Organization : Comparable<Organization> {
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

    /**
     * Уникальный id организации. Не может быть null. Значение поля должно быть больше 0
     */
    private var id: Long? = -1

    /**
     * Имя организации. Не может быть null. Строка не может быть пустой
     */
    private var name: String?
        set(name) {
            if (nameIsValid(name))
                field = name
            else
                throw Exception()
        }

    /**
     * Координаты организации. Не может быть null
     */
    private var coordinates: Coordinates?
        set(coordinates) {
            when (coordinates) {
                null -> throw Exception()
                else -> field = coordinates
            }
        }

    /**
     * Дата создания организации (генерируется автоматически). Не может быть null
     */
    @Serializable(with = DateAsLongSerializer::class)
    private var creationDate: Date?

    /**
     * Годовой оборот организации. Не может быть null. Значение поля должно быть больше 0
     */
    private var annualTurnover: Int?
        set(annualTurnover) {
            if (annualTurnover == null || annualTurnover <= 0)
                throw Exception()
            field = annualTurnover
        }

    /**
     * Полное имя организации. Длина строки не должна быть больше 925. Не может быть null
     */
    private var fullName: String?
        set(fullName) {
            if (fullName == null || fullName.length in 0..925)
                field = fullName
            else
                throw Exception()
        }

    /**
     * Количество работников в организации. Не может быть null. Значение поля должно быть больше 0
     */
    private var employeesCount: Long?
        set(employeesCount) {
            if (employeesCount == null || employeesCount > 0)
                field = employeesCount
            else
                throw Exception()
        }

    /**
     * Тип организации. Не может быть null
     */
    private var type: OrganizationType?
        set(type) {
            when (type) {
                null -> throw Exception()
                else -> field = type
            }
        }

    /**
     * Почтовый адрес организации. Не может быть null
     */
    private var postalAddress: Address? // Поле может быть null

    fun setId(id: Long?) {
        this.id = id
    }

    fun getId(): Long? = this.id
    fun getFullName(): String? = this.fullName
    fun getPostalAddress(): Address? = this.postalAddress
    fun getEmployeesCount(): Long? = this.employeesCount

    /**
     * Проверяет объект на соответствие объекта класса требованиям.
     */
    fun objectIsValid(): Boolean = idIsValid(id) && nameIsValid(name) && coordinatesIsValid(coordinates) &&
            annualTurnoverIsValid(annualTurnover) && fullNameIsValid(fullName) &&
            employeesCountIsValid(employeesCount) && typeIsValid(type) &&
            (postalAddress == null || Address.zipCodeIsValid(postalAddress?.getZipCode()))

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
        return "id: \u001B[34m$id\u001B[39m\n" +
                "название организации: \u001B[34m$name\u001B[39m\n" +
                "координаты: \u001B[34m${coordinates.toString()}\u001B[39m\n" +
                "дата создания: \u001B[34m$creationDate\u001B[39m\n" +
                "годовой оборот: \u001B[34m$annualTurnover\u001B[39m\n" +
                "полное имя: \u001B[34m$fullName\u001B[39m\n" +
                "количество сотрудников: \u001B[34m$employeesCount\u001B[39m\n" +
                "тип: \u001B[34m$type\u001B[39m\n" +
                "почтовый адрес: \u001B[34m$postalAddress\u001B[39m"
    }

    override fun compareTo(other: Organization): Int {
        if (this.annualTurnover != other.annualTurnover) {
            if (this.annualTurnover == null)
                return 1
            if (other.annualTurnover == null)
                return -1
            return if (other.annualTurnover!! < this.annualTurnover!!) 1 else -1
        }

        if (this.employeesCount != other.employeesCount) {
            if (this.employeesCount == null)
                return 1
            if (other.employeesCount == null)
                return -1
            return if (other.employeesCount!! < this.employeesCount!!) 1 else -1
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

    fun getZipCode(): String? = this.zipCode

    override fun toString(): String {
        return zipCode.toString()
    }
}

@Serializable
enum class OrganizationType {
    COMMERCIAL, GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY, OPEN_JOINT_STOCK_COMPANY
}