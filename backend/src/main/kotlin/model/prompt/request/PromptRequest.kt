package model.prompt.request

import kotlinx.serialization.Serializable

@Serializable
data class PromptRequest(
    val modelUri: String,
    val completionOptions: CompletionOptions,
    val messages: List<Message>
) {
    init {
        if (messages.size < 2)
            throw Exception("PromptRequest must have more than 1 message")
    }
}
