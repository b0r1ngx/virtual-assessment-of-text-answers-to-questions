package dev.boringx.model.prompt.response

import dev.boringx.model.prompt.request.Message
import kotlinx.serialization.Serializable

@Serializable
data class Alternative(
    val message: Message,
    val status: String
)