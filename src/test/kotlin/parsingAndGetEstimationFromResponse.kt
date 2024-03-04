import dev.boringx.utils.getEstimationFromPromptResponse
import utils.getStringFromJsonFile
import kotlin.test.assertEquals

internal fun parsingAndGetEstimationFromResponseTest() {
    val rawJsonToAnswers = listOf(
        "first-steps-with-yagpt/prompt-response-200.json" to 10,
        "first-steps-with-yagpt/prompt-response-at-web-to-documentation-style.json" to 8,
    )
    for (rawJsonToAnswer in rawJsonToAnswers) {
        val promptResponse = getStringFromJsonFile(rawJsonToAnswer.first)
        assertEquals(
            actual = getEstimationFromPromptResponse(promptResponse),
            expected = rawJsonToAnswer.second
        )
    }
}
