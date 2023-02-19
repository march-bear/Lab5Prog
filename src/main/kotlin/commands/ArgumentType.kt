package commands

import kotlinx.serialization.descriptors.PrimitiveKind

data class ArgumentType(val type: PrimitiveKind, val nullable: Boolean, val limit: Byte)