import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.valiktor.ConstraintViolationException
import org.valiktor.functions.*
import org.valiktor.validate
import java.util.Date


@Serializable
class Organization() : Comparable<Organization> {
    companion object {
        fun idIsValid(id: Long): Boolean {
            return id > 0
        }
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

        val fieldRequirements =
            "${String.format("%-25s", "Название организации")}\u001B[34m- непустая строка\u001B[39m\n" +
                    "${String.format("%-25s", "Координата x")}\u001B[34m- число с плавающей точкой\u001B[39m\n" +
                    "${String.format("%-25s", "Координата y")}\u001B[34m- целое число\u001B[39m\n" +
                    "${String.format("%-25s", "Годовой оборот")}\u001B[34m- положительное целое число\u001B[39m\n" +
                    "${String.format("%-25s", "Полное имя")}\u001B[34m- непустая строка длины не больше 925 или null\u001B[39m\n" +
                    "${String.format("%-25s", "Количество сотрудников")}\u001B[34m- положительное целое число или null\u001B[39m\n" +
                    "${String.format("%-25s", "Тип")}\u001B[34m- COMMERCIAL, GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY или OPEN_JOINT_STOCK_COMPANY,\u001B[39m\n" +
                    "${String.format("%-25s", "Почтовый адрес")}\u001B[34m- непустая строка или null\u001B[39m"
    }

    var id: Long = -1
        set(value) {
            field = value
            validate(this) { validate(Organization::id).isPositive() }
        }
    var name: String = ""
        set(value) {
            field = value
            validate(this) { validate(Organization::name).isNotEmpty() }
        }
    var coordinates: Coordinates = Coordinates()

    @Serializable(with = DateAsLongSerializer::class)
    private var creationDate: Date = Date()

    var annualTurnover: Int = 0
        set(value) {
            field = value
            validate(this) { validate(Organization::annualTurnover).isPositive() }
        }

    var fullName: String? = null
        set(value) {
            field = value
            validate(this) { validate(Organization::fullName).isValid { it.length in 0..925 } }
        }

    var employeesCount: Long? = null
        set(value) {
            field = value
            if (field != null)
                validate(this) { validate(Organization::employeesCount).isPositive() }
        }
    var type: OrganizationType = OrganizationType.COMMERCIAL
    var postalAddress: Address? = null

    fun objectIsValid(): Boolean = try {
        validate(this) {
            validate(Organization::id).isPositive()
            validate(Organization::name).isNotEmpty()
            validate(Organization::annualTurnover).isPositive()
            validate(Organization::fullName).isValid { it.length in 0..925 }
        }
        true
    } catch (ex: ConstraintViolationException) { false }

    constructor(name: String, coordinates: Coordinates, annualTurnover: Int, fullName: String?,
                employeesCount: Long?, type: OrganizationType, postalAddress: Address?) : this() {
        this.name = name
        this.coordinates = coordinates
        this.annualTurnover = annualTurnover
        this.fullName = fullName
        this.employeesCount = employeesCount
        this.type = type
        this.postalAddress = postalAddress
    }

    override fun toString(): String {
        return "${String.format("%-25s", "ID:")}\u001B[34m$id\u001B[39m\n" +
                "${String.format("%-25s", "Название организации:")}\u001B[34m$name\u001B[39m\n" +
                "${String.format("%-25s", "Координаты:")}\u001B[34m$coordinates\u001B[39m\n" +
                "${String.format("%-25s","Дата создания:")}\u001B[34m$creationDate\u001B[39m\n" +
                "${String.format("%-25s","Годовой оборот:")}\u001B[34m$annualTurnover\u001B[39m\n" +
                "${String.format("%-25s","Полное имя:")}\u001B[34m$fullName\u001B[39m\n" +
                "${String.format("%-25s","Количество сотрудников:")}\u001B[34m$employeesCount\u001B[39m\n" +
                "${String.format("%-25s","Тип:")}\u001B[34m$type\u001B[39m\n" +
                "${String.format("%-25s","Почтовый адрес:")}\u001B[34m$postalAddress\u001B[39m"
    }

    override fun compareTo(other: Organization): Int {
        if (this.annualTurnover != other.annualTurnover)
            return if (other.annualTurnover < this.annualTurnover) 1 else -1

        if (this.employeesCount != other.employeesCount) {
            if (this.employeesCount == null)
                return 1
            if (other.employeesCount == null)
                return -1
            return if (other.employeesCount!! < this.employeesCount!!) 1 else -1
        }
        return 0
    }

    fun clone(): Organization {
        val newOrganization = Organization(name, coordinates.clone(), annualTurnover, fullName,
            employeesCount?.toLong(), type, postalAddress?.clone())
        newOrganization.id = id
        newOrganization.creationDate = creationDate
        return newOrganization
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Organization

        if (id != other.id) return false
        if (name != other.name) return false
        if (coordinates != other.coordinates) return false
        if (creationDate != other.creationDate) return false
        if (annualTurnover != other.annualTurnover) return false
        if (fullName != other.fullName) return false
        if (employeesCount != other.employeesCount) return false
        if (type != other.type) return false
        if (postalAddress != other.postalAddress) return false

        return true
    }
}

@Serializable
class Coordinates(
    var x: Double = 0.0,
    var y: Int = 0,
) {
    override fun toString(): String {
        return "$x $y"
    }
    fun clone(): Coordinates {
        return Coordinates(x, y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinates

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }
}

@Serializable
class Address(var zipCode: String) {
    override fun toString(): String {
        return zipCode
    }

    fun clone(): Address {
        return Address(zipCode)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (zipCode != other.zipCode) return false

        return true
    }
}

@Serializable
enum class OrganizationType {
    COMMERCIAL,
    GOVERNMENT,
    TRUST,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY,
}