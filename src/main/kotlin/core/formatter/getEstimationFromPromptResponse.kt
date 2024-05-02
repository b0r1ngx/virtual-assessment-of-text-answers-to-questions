package dev.boringx.core.formatter

import dev.boringx.model.prompt.response.PromptResponse
import dev.boringx.utils.decodeFromStringSafety

private val INT_PATTERN = Regex("\\d+")

// TODO: Помимо оценки, также можно сохранять и выдавать полный ответ (комментарий) БЯМ на ответ.
//        (полезно, для ответа на вопрос: почему была поставлена такая оценка?)
fun getAssessmentFromPromptResponse(response: String): Int {
    try {
        val deserializing =
            response.decodeFromStringSafety<PromptResponse.Success>()

        val promptResponse = deserializing
            ?.result?.alternatives?.firstOrNull()?.message?.text ?: ""

        val assessment = INT_PATTERN.find(promptResponse)
        return assessment?.value?.toIntOrNull() ?: -1
    } catch (e: Exception) {
        println(e.stackTrace)
        return -1
    }
}