package requests

import Organization
import java.util.*

class SaveRequest(private val fileToSave: String) : Request {
    override fun process(collection: LinkedList<Organization>): Boolean {

        return true
    }

}