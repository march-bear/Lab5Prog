import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isPositive
import org.valiktor.validate
import java.lang.Exception
import java.lang.System.currentTimeMillis
import java.util.Date
import kotlin.math.max


@Serializable
class Organization : Comparable<Organization> {
    companion object {
        fun idIsValid(id: Long): Boolean = id > 0
        fun nameIsValid(name: String): Boolean = name != ""
        fun annualTurnoverIsValid(annualTurnover: Int): Boolean = annualTurnover > 0
        fun fullNameIsValid(fullName: String?): Boolean = fullName == null || fullName.length in 0..925
        fun employeesCountIsValid(employeesCount: Long?): Boolean = employeesCount == null || employeesCount > 0
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
    // valiktor
    private var id: Long = -1
    private var name: String
    private var coordinates: Coordinates

    @Serializable(with = DateAsLongSerializer::class)
    private var creationDate: Date

    private var annualTurnover: Int
        set(annualTurnover) {
            if (annualTurnover <= 0)
                throw Exception()
            field = annualTurnover
        }

    private var fullName: String?
        set(fullName) {
            if (fullName == null || fullName.length in 0..925)
                field = fullName
        }

    private var employeesCount: Long?
    private var type: OrganizationType
    private var postalAddress: Address?

    fun setId(id: Long) {
        this.id = id
    }
    fun getId(): Long = this.id
    fun getFullName(): String? = this.fullName
    fun getPostalAddress(): Address? = this.postalAddress
    fun getEmployeesCount(): Long? = this.employeesCount

    fun objectIsValid(): Boolean = idIsValid(id ?: -1) && nameIsValid(name) &&
            annualTurnoverIsValid(annualTurnover) && fullNameIsValid(fullName) &&
            employeesCountIsValid(employeesCount)

    constructor(name: String, coordinates: Coordinates, annualTurnover: Int, fullName: String?,
                employeesCount: Long?, type: OrganizationType, postalAddress: Address?) {
        this.name = name
        this.coordinates = coordinates
        this.creationDate = Date((Math.random() * currentTimeMillis()).toLong())
        this.annualTurnover = annualTurnover
        this.fullName = fullName
        this.employeesCount = employeesCount
        this.type = type
        this.postalAddress = postalAddress
    }

    init {
        validate(this) {
            validate(Organization::id).isPositive()
            validate(Organization::name).isNotEmpty()
            validate(Organization::fullName).hasSize(0, 925)
        }
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
}

@Serializable
class Coordinates(
    private val x: Double,
    private val y: Int,
) {
    override fun toString(): String {
        return "$x $y"
    }
}

@Serializable
class Address(val zipCode: String) {
    override fun toString(): String {
        return zipCode
    }
}

@Serializable
enum class OrganizationType {
    COMMERCIAL, GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY, OPEN_JOINT_STOCK_COMPANY,
}