import core.formatter.getAssessmentFromPromptResponse
import utils.getStringFromJsonFile
import kotlin.test.assertEquals

internal fun parsingAndGetAssessmentFromResponseTest() {
    val rawJsonToAnswers = listOf(
        "1st-steps-with-yagpt/prompt-response-200.json" to 10,
        "1st-steps-with-yagpt/prompt-response-at-web-to-documentation-style.json" to 8,
    )
    for ((rawJson, answer) in rawJsonToAnswers) {
        val promptResponse = getStringFromJsonFile(rawJson)
        assertEquals(
            actual = getAssessmentFromPromptResponse(promptResponse),
            expected = answer
        )
    }
}
