package dev.boringx.datalayer.prompt.request

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String, // Role
    val text: String
)
