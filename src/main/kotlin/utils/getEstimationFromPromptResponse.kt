package dev.boringx.utils

import dev.boringx.datalayer.prompt.response.PromptResponse
import kotlinx.serialization.json.Json

private val INT_PATTERN = Regex("\\d+")

fun getEstimationFromPromptResponse(response: String): Int {
    try {
        val deserializing = Json
            .decodeFromString<PromptResponse>(response)
        val promptResponse = deserializing
            .result.alternatives.first().message.text
        val int = INT_PATTERN.find(promptResponse)
        return int?.value?.toInt() ?: -1
    } catch (e: Exception) {
        println(e)
        return -1
    }
}