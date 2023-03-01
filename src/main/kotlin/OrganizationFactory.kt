import exceptions.InvalidFieldValueException
import iostreamers.EventMessage
import iostreamers.Reader
import iostreamers.TextColor
import org.valiktor.ConstraintViolationException
import java.util.function.Consumer

class OrganizationFactory(private val reader: Reader? = null) {
    private fun newOrganizationFromScript(): Organization {
        val newOrganization = Organization()
        getValueForField { newOrganization.name = reader!!.readString() }

        getValueForField { newOrganization.coordinates.x = reader!!.readString().toDoubleOrNull()
            ?: throw NumberFormatException("Ожидалось дробное числовое значение для поля: x") }

        getValueForField { newOrganization.coordinates.y = reader!!.readString().toIntOrNull()
            ?: throw NumberFormatException("Ожидалось целочисленное значение для поля: y") }

        getValueForField { newOrganization.annualTurnover = reader!!.readString().toIntOrNull()
            ?: throw NumberFormatException("Ожидалось целочисленное значение для поля: годовой оборот") }

        getValueForField { newOrganization.fullName = reader!!.readStringOrNull() }

        getValueForField { newOrganization.employeesCount = reader!!.readStringOrNull()?.toLong() }

        getValueForField { newOrganization.type = OrganizationType.valueOf(reader!!.readString()) }

        getValueForField {
            val input = reader!!.readStringOrNull()
            if (input == null)
                newOrganization.postalAddress = null
            else
                newOrganization.postalAddress = Address(input)
        }

        return newOrganization
    }

    fun newOrganizationFromInput(): Organization {
        if (reader != null)
            return newOrganizationFromScript()

        val r = Reader()

        val newOrganization = Organization()

        readValueForField("Имя организации") {
            newOrganization.name = r.readString()
        }

        EventMessage.printMessage("Координаты")
        readValueForField("x") {
            newOrganization.coordinates.x = r.readString().toDoubleOrNull()
                ?: throw NumberFormatException("Введите дробное числовое значение")
        }

        readValueForField("y") {
            newOrganization.coordinates.y = r.readString().toIntOrNull()
                ?: throw NumberFormatException("Введите целочисленное значение")
        }

        readValueForField("Годовой оборот") {
            newOrganization.annualTurnover = r.readString().toIntOrNull()
                ?: throw NumberFormatException("Введите целочисленное значение")
        }

        readValueForField("Полное имя") { newOrganization.fullName = r.readStringOrNull() }

        readValueForField("Количество сотрудников") {
            newOrganization.employeesCount = r.readStringOrNull()?.toLong()
        }

        readValueForField(
            "Тип организации " +
                    "(COMMERCIAL, GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY или OPEN_JOINT_STOCK_COMPANY)"
        ) {
            newOrganization.type = OrganizationType.valueOf(r.readString())
        }

        readValueForField("Адрес (ZIP-код)") {
            val input = r.readStringOrNull()
            if (input == null)
                newOrganization.postalAddress = null
            else
                newOrganization.postalAddress = Address(input)
        }

        return newOrganization
    }

    private fun readValueForField(message: String, consumer: Consumer<Unit>) {
        while (true) {
            try {
                EventMessage.inputPrompt(message, ": ")
                consumer.accept(Unit)
                break
            } catch (ex: ConstraintViolationException) {
                EventMessage.printMessage(
                    EventMessage.message("Невалидное значение поля, повторите ввод", TextColor.RED)
                )
            } catch (ex: IllegalArgumentException) {
                EventMessage.printMessage(
                    EventMessage.message("Невалидное значение поля. " +
                            "Скорее всего аргумент имеет другой тип. Повторите ввод", TextColor.RED)
                )
            } catch (ex: NumberFormatException) {
                EventMessage.printMessage(
                    EventMessage.message(ex.message ?: "Введите числовое значение", TextColor.RED)
                )
            }
        }
    }

    private fun getValueForField(consumer: Consumer<Unit>) {
        try {
            consumer.accept(Unit)
        } catch (ex: ConstraintViolationException) {
            throw InvalidFieldValueException(
                "Невалидное значение поля."
            )
        } catch (ex: IllegalArgumentException) {
            throw InvalidFieldValueException(
                "Невалидное значение поля. Скорее всего аргумент имеет другой тип."
            )
        } catch (ex: NumberFormatException) {
            throw InvalidFieldValueException(ex.message)
        }
    }
}