import java.io.Reader

class OrganizationFactory(private val script: String? = null) {
    // FIXME
    fun newOrganization(): Organization {
        return Organization("",
            Coordinates(1.0, 2),
            0,
            "",
            0,
            OrganizationType.COMMERCIAL,
            Address("")
        )
    }
}