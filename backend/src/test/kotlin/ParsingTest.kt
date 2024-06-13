import core.formatter.getAssessmentFromPromptResponse
import model.prompt.request.CompletionOptions
import model.prompt.request.Message
import model.prompt.request.PromptRequest
import model.prompt.request.Role
import org.junit.Test
import utils.encodeToString
import utils.getStringFromJsonFile
import kotlin.test.assertEquals

class ParsingTest {

    @Test
    fun parsingAndGetAssessmentFromResponseTest() {
        val rawJsonToAnswers = listOf(
            "1st-steps-with-yagpt/prompt-response-200.json" to 10,
            "1st-steps-with-yagpt/prompt-response-at-web-to-documentation-style.json" to 8,
        )
        for ((rawJson, answer) in rawJsonToAnswers) {
            val promptResponse = getStringFromJsonFile(rawJson)
            assertEquals(
                expected = answer,
                actual = getAssessmentFromPromptResponse(promptResponse),
            )
        }
    }

    @Test
    fun comparePromptRequestsFromJsonAndSerializableTest() {
        val promptRequest = getStringFromJsonFile(
            fileName = "1st-steps-with-yagpt/prompt-request-template.json"
        ).replace(oldValue = ": ", newValue = ":")

        val data = PromptRequest(
            "gpt://b1gjcmmah16shmb9g8hq/yandexgpt-lite",
            CompletionOptions(
                stream = false,
                temperature = 0.6f,
                maxTokens = "2000"
            ),
            messages = listOf(
                Message(
                    role = Role.system.name,
                    text = "Найди ошибки в тексте и исправь их"
                ),
                Message(
                    role = Role.user.name,
                    text = "Ламинат подойдет для укладке на кухне или в детской комнате – он не боиться влаги и механических повреждений благодаря защитному слою из облицованных меламиновых пленок толщиной 0,2 мм и обработанным воском замкам."
                )
            )
        )

        assertEquals(
            expected = promptRequest,
            actual = data.encodeToString(),
        )
    }
}