package dev.boringx.datalayer.prompt.response

data class Usage(
    val inputTextTokens: String,
    val completionTokens: String,
    val totalTokens: String
)