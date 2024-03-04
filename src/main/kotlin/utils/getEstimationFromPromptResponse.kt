package dev.boringx.utils

import dev.boringx.model.prompt.response.PromptResponse

private val INT_PATTERN = Regex("\\d+")

fun getAssessmentFromPromptResponse(response: String): Int {
    try {
        val deserializing =
            response.decodeFromStringSafety<PromptResponse.Success>()
        val promptResponse = deserializing
            ?.result?.alternatives?.first()?.message?.text ?: ""
        val int = INT_PATTERN.find(promptResponse)
        return int?.value?.toInt() ?: -1
    } catch (e: Exception) {
        println(e.stackTrace)
        return -1
    }
}