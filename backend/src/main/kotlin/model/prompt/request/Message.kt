package model.prompt.request

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String,
    val text: String,
)
