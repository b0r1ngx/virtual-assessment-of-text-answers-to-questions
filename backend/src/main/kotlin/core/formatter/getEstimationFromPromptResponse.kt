package core.formatter

import model.prompt.response.PromptResponse
import utils.decodeFromStringSafety

private val INT_PATTERN = Regex("\\d+")

// TODO: Помимо оценки, также можно сохранять и выдавать полный ответ (комментарий) БЯМ на ответ.
//        (полезно, для ответа на вопрос: почему была поставлена такая оценка?)
fun getAssessmentFromPromptResponse(response: String): Int {
    val deserializing =
        response.decodeFromStringSafety<PromptResponse.Success>()

    val promptResponse = deserializing
        ?.result?.alternatives?.firstOrNull()?.message?.text ?: ""

    val assessment = INT_PATTERN.find(promptResponse)
    return assessment?.value?.toIntOrNull() ?: -1
}