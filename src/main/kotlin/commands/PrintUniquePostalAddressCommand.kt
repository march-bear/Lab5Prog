package commands

import Organization
import exceptions.InvalidArgumentsForCommandException
import iostreamers.EventMessage
import java.util.LinkedList

class PrintUniquePostalAddressCommand(private val collection: LinkedList<Organization>) : Command {
    override fun execute(args: String?) {
        if (args != null)
            throw InvalidArgumentsForCommandException("Команда не принимает аргументы")

        if (collection.isEmpty()) {
            EventMessage.messageln("Коллекция пуста", TextColor.BLUE)
            return
        }

        val postalAddresses: HashSet<String> = HashSet()
        for (elem in collection) {
            val postalAddress = elem.getPostalAddress()
            if (postalAddress != null) {
                postalAddresses.add(postalAddress.getZipCode().toString())
            }
        }

        for (elem in postalAddresses)
            EventMessage.messageln(elem, TextColor.BLUE)
        println()
    }

    override fun getInfo(): String = "вывести уникальные значения поля postalAddress всех элементов в коллекции"
}