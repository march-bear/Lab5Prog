package iostreamers

import command.CommandArgument
import command.CommandData
import java.util.*
import java.util.regex.Pattern

/**
 * Класс для считывания данных с входного потока
 */

class Reader(private val sc: Scanner = Scanner(System.`in`)) {
    fun readStringOrNull(): String? {
        val input = readString()
        return if (input != "") input else null
    }
    fun readString(): String {
        return sc.nextLine().trim()
    }

    fun readCommand(): CommandData? {
        if (!sc.hasNextLine())
            return null
        val commandList = readString().split(Pattern.compile("\\s+"), limit = 2)
        return CommandData(commandList[0], CommandArgument(if (commandList.size == 2) commandList[1] else null))
    }
}
