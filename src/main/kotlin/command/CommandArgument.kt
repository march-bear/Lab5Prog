package command

import Organization
import exceptions.InvalidArgumentsForCommandException
import java.lang.NullPointerException
import java.util.LinkedList
import java.util.regex.Pattern


class CommandArgument(argumentString: String? = null) {
    val primitiveTypeArguments: List<String>?
    val organizations: LinkedList<Organization> = LinkedList()

    private var organizationLimit = -1

    init {
        primitiveTypeArguments = if (argumentString == null || argumentString.trim() == "") {
            null
        } else {
            val matcher = Pattern.compile(
                "(?<=^|\\s)\"(.*?)\"(?=\\s|\$)|(?<=^|\\s)(.*?)(?=\\s|\$)"
            ).matcher(argumentString.trim())

            val tmpArgs = ArrayList<String>()
            while (matcher.find())
                tmpArgs.add(matcher.group())
            tmpArgs
        }
    }
    fun addOrganization(org: Organization): Boolean {
        if (organizations.size == organizationLimit)
            return false

        organizations.add(org)
        return true
    }

    fun checkArguments(
        argumentTypes: List<ArgumentType>,
    ): Int {
        if (argumentTypes.sorted() != argumentTypes)
            throw Exception("Описание аргументов в классе введенной команды некорректно, выполнение невозможно. " +
                    "Обратитесь в поддержку для выяснения подробностей: razmech404@tals.ya")

        var organizationCounter = 0
        var counter = 0
        for (type in argumentTypes) {
            try {
                when (type) {
                    ArgumentType.INT -> primitiveTypeArguments?.get(counter)!!.toInt()
                    ArgumentType.LONG -> primitiveTypeArguments?.get(counter)!!.toLong()
                    ArgumentType.FLOAT -> primitiveTypeArguments?.get(counter)!!.toFloat()
                    ArgumentType.DOUBLE -> primitiveTypeArguments?.get(counter)!!.toDouble()
                    ArgumentType.STRING -> primitiveTypeArguments?.get(counter)!!.toString()
                    ArgumentType.ORGANIZATION -> { organizationCounter++; counter--}
                }
            } catch (ex: NumberFormatException) {
                throw InvalidArgumentsForCommandException("${primitiveTypeArguments?.get(counter) ?: ""}: " +
                            "аргумент не удовлетворяет условию type=$type")
            } catch (ex: NullPointerException) {
                throw InvalidArgumentsForCommandException(("Аргумент $type - не найден"))
            }
            counter++
        }

        if (counter != (primitiveTypeArguments?.size ?: 0))
            throw InvalidArgumentsForCommandException(
                if (organizationCounter != 0)
                    "аргумент - объект класса Organization - вводится на следующих строках"
                else
                    "${primitiveTypeArguments?.get(counter)}: неизвестный аргумент")

        organizationLimit = organizationCounter
        return organizationLimit
    }
}