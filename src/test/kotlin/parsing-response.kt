import dev.boringx.utils.getEstimationFromPromptResponse
import java.io.File
import kotlin.test.assertEquals

internal fun parsingResponseTest() {
    val rawJsonToAnswers = listOf(
        "prompt-response-200.json" to 10,
        "prompt-response-at-web-to-documentation-style.json" to 8,
    )
    for (rawJsonToAnswer in rawJsonToAnswers) {
        var promptResponse = ""
        File("./src/main/resources/${rawJsonToAnswer.first}")
            .readLines(charset = Charsets.UTF_8)
            .forEach { promptResponse += it.trim() }

        assertEquals(
            actual = rawJsonToAnswer.second,
            expected = getEstimationFromPromptResponse(promptResponse)
        )
    }
}
