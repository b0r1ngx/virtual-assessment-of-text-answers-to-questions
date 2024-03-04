package dev.boringx.model.prompt.request

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

        if (maxTokens.toInt() >= 8000)
            throw Exception("maxTokens must <=2000 (in request/response), <=8000 in sum")
    }

    companion object {
        val default = CompletionOptions(
            stream = false,
            temperature = 0.6f,
            maxTokens = "1000"
        )
    }
}