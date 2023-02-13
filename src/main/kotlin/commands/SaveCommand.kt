package commands

import exceptions.InvalidArgumentsForCommandException
import java.util.regex.Pattern

class SaveCommand : Command {
    override fun execute(args: String?) {
        val fileNames = args?.split(Pattern.compile("\\s+"))
        when (fileNames?.size) {
            0, null -> throw InvalidArgumentsForCommandException("Имя файла для сохранения не указано")
            1 -> {
                println(fileNames[0])
                println(System.getProperty("user.dir"))
            }
            else -> throw InvalidArgumentsForCommandException("Нужно указать путь к одному файлу")
        }
    }

    override fun getInfo(): String = "сохранить коллекцию в файл"
}