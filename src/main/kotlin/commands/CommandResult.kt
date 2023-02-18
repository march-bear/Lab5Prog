package commands

import requests.Request

data class CommandResult(
    val commandCompleted: Boolean,
    val request: Request? = null,
    val exception: Exception? = null,
    val message: String? = null,
)