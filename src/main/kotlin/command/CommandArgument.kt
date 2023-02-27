package command

import exceptions.InvalidArgumentsForCommandException
import java.lang.NullPointerException
import java.util.regex.Pattern


class CommandArgument(argumentString: String? = null) {
    val args: List<String>?

    init {
        args = if (argumentString == null || argumentString.trim() == "") {
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


    fun checkArguments(
        argumentTypes: List<ArgumentTypeData>,
        messageIfMore: String = "",
        messageIfError: String = messageIfMore,
    ): Boolean {
        if (argumentTypes.sorted() != argumentTypes)
            throw Exception("Описание аргументов в классе введенной команды некорректно, выполнение невозможно. " +
                    "Обратитесь в поддержку для выяснения подробностей: razmech404@tals.ya")

        if (argumentTypes.size > (args?.size ?: 0))
            throw InvalidArgumentsForCommandException(messageIfMore)

        var counter = 0
        var organizationCounter = 0
        for (type in argumentTypes) {
            try {
                when (type.type) {
                    ArgumentType.INT -> {
                        args?.get(counter)!!.toInt()
                    }

                    ArgumentType.LONG -> {

                    }

                    ArgumentType.FLOAT -> {

                    }

                    ArgumentType.DOUBLE -> {

                    }

                    ArgumentType.STRING -> {

                    }

                    ArgumentType.ORGANIZATION -> {
                        if (type.nullable)
                            throw Exception(
                                "Описание аргументов в классе введенной команды некорректно, выполнение невозможно. " +
                                        "Обратитесь в поддержку для выяснения подробностей: razmech404@tals.ya"
                            )
                        organizationCounter++
                    }
                }
            } catch (ex: NumberFormatException) {
                if (type.nullable)
                    1
                else
                    throw InvalidArgumentsForCommandException("${args?.get(counter) ?: ""}: " +
                            "аргумент не удовлетворяет условию type=${type.type}")
            } catch (ex: NullPointerException) {
                if (type.nullable)
                    1
                else
                    throw InvalidArgumentsForCommandException(("Аргумент ${type.type} - не найден"))
            }
        }
        return true
    }
}