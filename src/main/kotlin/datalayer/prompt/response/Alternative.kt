package dev.boringx.datalayer.prompt.response

import dev.boringx.datalayer.prompt.request.Message

data class Alternative(
    val message: Message,
    val status: String
)