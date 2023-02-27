package requests

import Organization
import java.util.*

interface Request {
    fun process(collection: LinkedList<Organization>): String
}