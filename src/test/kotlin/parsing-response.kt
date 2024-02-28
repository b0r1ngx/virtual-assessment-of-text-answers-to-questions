import dev.boringx.utils.getEstimationFromPromptResponse
import java.io.File
import kotlin.test.assertEquals

// TODO: Add it to run-all-tests.kt
fun parsingResponseTest() {
    val responses = listOf(
        "prompt-response-200.json",
        "prompt-response-at-web-to-documentation-style.json",
    )
    for (response in responses) {
        var promptResponse = ""
        val rawJsonList = File(
            "./src/main/resources/$response"
        ).readLines(charset = Charsets.UTF_8)
        for (line in rawJsonList)
            promptResponse += line.trim()

        println(promptResponse)

        val estimation = getEstimationFromPromptResponse(promptResponse)
        println(estimation)
        assertEquals(
            actual = "TODO()",
            expected = "TODO()"
        )
    }
}
