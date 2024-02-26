package dev.boringx.datalayer.prompt.request

data class CompletionOptions(
    val stream: Boolean,
    val temperature: Float,
    val maxTokens: String // Int.toString()
)