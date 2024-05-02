package dev.boringx.utils

import dev.boringx.model.prompt.response.PromptResponse

private val INT_PATTERN = Regex("\\d+")

// TODO: Помимо оценки, также можно сохранять и выдавать полный ответ (комментарий) БЯМ на ответ.
//        (полезно, для ответа на вопрос: почему была поставлена такая оценка?)
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