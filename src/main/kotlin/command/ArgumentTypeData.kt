package command

data class ArgumentTypeData(val type: ArgumentType, val nullable: Boolean): Comparable<ArgumentTypeData> {
    override fun compareTo(other: ArgumentTypeData): Int {
        if (this.nullable != other.nullable)
            return if (this.nullable) -1 else 1
        if (this.type != other.type)
            return if (this.type > other.type) 1 else -1
        return 0
    }

}

enum class ArgumentType {
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    STRING,
    ORGANIZATION,
}