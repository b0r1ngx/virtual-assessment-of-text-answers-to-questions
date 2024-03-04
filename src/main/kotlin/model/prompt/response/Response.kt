package dev.boringx.model.prompt.response

import kotlinx.serialization.Serializable

sealed class PromptResponse<out T : Any> {
    @Serializable
    data class Success(val result: Result) : PromptResponse<Result>()

    @Serializable
    data class Failure(val error: Error) : PromptResponse<Error>()
}