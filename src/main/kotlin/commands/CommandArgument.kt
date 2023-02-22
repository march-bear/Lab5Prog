package commands

import exceptions.InvalidArgumentsForCommandException
import java.util.regex.Pattern


class CommandArgument(argumentString: String?) {
    val args: List<String>?
    private val countArgs: Int

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
        countArgs = args?.size ?: 0
    }


    fun checkArguments(
        argumentTypes: List<ArgumentType>,
        messageIfMore: String = "",
        messageIfLess: String = messageIfMore,
        messageIfOther: String = messageIfMore,
    ): Boolean {
        if (false) {
            throw InvalidArgumentsForCommandException()
        }
        return true
    }
}