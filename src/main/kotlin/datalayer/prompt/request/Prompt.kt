package dev.boringx.datalayer.prompt.request

data class Prompt(
    val modelUri: String,
    val completionOptions: CompletionOptions,
    val messages: List<Message>
)
