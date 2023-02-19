import java.io.Reader

class OrganizationFactory(private val script: String? = null) {
    fun newOrganizationFromInput(): Organization {
        if (script != null)
            return newOrganizationFromScript()
        val reader = Reader
    }

    fun newOrganizationFromScript(): Organization {

    }
}