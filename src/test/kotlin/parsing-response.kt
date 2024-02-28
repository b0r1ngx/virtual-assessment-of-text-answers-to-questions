import dev.boringx.utils.getEstimationFromPromptResponse
import java.io.File
import kotlin.test.assertEquals

internal fun parsingResponseTest() {
    val responses = listOf(
        "prompt-response-200.json" to 10,
        "prompt-response-at-web-to-documentation-style.json" to 8,
    )
    for (response in responses) {
        var promptResponse = ""
        val rawJsonList = File(
            "./src/main/resources/${response.first}"
        ).readLines(charset = Charsets.UTF_8)
        for (line in rawJsonList)
            promptResponse += line.trim()

        val estimation = getEstimationFromPromptResponse(promptResponse)
        assertEquals(
            actual = response.second,
            expected = estimation
        )
    }
}
