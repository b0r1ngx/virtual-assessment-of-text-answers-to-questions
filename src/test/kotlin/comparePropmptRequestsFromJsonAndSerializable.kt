import dev.boringx.model.prompt.request.CompletionOptions
import dev.boringx.model.prompt.request.Message
import dev.boringx.model.prompt.request.PromptRequest
import dev.boringx.model.prompt.request.Role
import dev.boringx.utils.encodeToString
import utils.getStringFromJsonFile
import kotlin.test.assertEquals

internal fun comparePromptRequestsFromJsonAndSerializableTest() {
    val promptResponse = getStringFromJsonFile(
        fileName = "1st-steps-with-yagpt/prompt-request-template.json"
    ).apply { replace(oldValue = ": ", newValue = ":") }

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
        actual = data.encodeToString(),
        expected = promptResponse
    )
}