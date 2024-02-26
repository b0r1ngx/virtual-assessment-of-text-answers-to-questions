package dev.boringx.datalayer.prompt.request

import kotlinx.serialization.Serializable

@Serializable
data class CompletionOptions(
    val stream: Boolean,
    val temperature: Float,
    val maxTokens: String
) {
    init {
        if (temperature !in .0f..1f)
            throw Exception("temperature must be between 0..1")
    }
}