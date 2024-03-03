package dev.boringx.model.prompt.response

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val alternatives: List<Alternative>,
    val usage: Usage,
    val modelVersion: String
)
