package dev.boringx.datalayer.prompt.request

import kotlinx.serialization.Serializable

@Serializable
data class CompletionOptions(
    val stream: Boolean,
    val temperature: Float,
    val maxTokens: String // Int.toString()
)