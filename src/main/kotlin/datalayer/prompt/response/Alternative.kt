package dev.boringx.datalayer.prompt.response

import dev.boringx.datalayer.prompt.request.Message
import kotlinx.serialization.Serializable

@Serializable
data class Alternative(
    val message: Message,
    val status: String
)