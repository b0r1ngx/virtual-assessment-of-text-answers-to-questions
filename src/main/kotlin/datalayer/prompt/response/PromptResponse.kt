package dev.boringx.datalayer.prompt.response

import dev.boringx.datalayer.prompt.request.Message

data class PromptResponse(
    val result: Result
)

data class Result(
    val alternatives: List<Alternative>,
    val usage: Usage,
    val modelVersion: String
)

data class Usage(
    val inputTextTokens: String,
    val completionTokens: String,
    val totalTokens: String
)

data class Alternative(
    val message: Message,
    val status: String
)
