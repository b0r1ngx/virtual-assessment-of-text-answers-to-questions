package dev.boringx.datalayer.prompt.response

import kotlinx.serialization.Serializable

@Serializable
data class PromptResponse(
    val result: Result
)
