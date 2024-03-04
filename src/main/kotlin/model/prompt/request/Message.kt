package dev.boringx.model.prompt.request

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String, // Role
    val text: String
)
