package iostreamers

import commands.CommandResult

/**
 * Перечисление, содержащее возможные цвета текста с их кодами
 */
enum class TextColor(val code: String) {
    RED("\u001B[31m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    GREEN("\u001B[32m"),
    DEFAULT("\u001B[39m"),
}