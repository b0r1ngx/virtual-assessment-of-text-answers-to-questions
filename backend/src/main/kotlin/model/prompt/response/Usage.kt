package model.prompt.response

import kotlinx.serialization.Serializable

@Serializable
data class Usage(
    val inputTextTokens: String,
    val completionTokens: String,
    val totalTokens: String
)