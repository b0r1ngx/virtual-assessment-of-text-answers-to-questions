package core

import core.formatter.getAssessmentFromPromptResponse
import logger.logger
import Answer
import model.ContextType
import Criterion
import Question
import model.prompt.request.CompletionOptions
import model.prompt.request.Message
import model.prompt.request.PromptRequest
import model.prompt.request.Role
import utils.createModelUri
import utils.preparePrompt
import java.util.logging.Level

fun Answer.getAssessment(
    question: Question,
    criteria: List<Criterion> = Criterion.entries,
    modelUri: String = createModelUri(),
    completionOptions: CompletionOptions = CompletionOptions.default
): Float {
    val responses = mutableListOf<String>()
    val assessments = mutableListOf<Int>()

    val questionToAnswerPrompt = preparePrompt(question, this)
    for (criterion in criteria) {
        val prompt = PromptRequest(
            modelUri = modelUri,
            completionOptions = completionOptions,
            messages = listOf(
                Message(
                    role = Role.system.name,
                    text = ContextType.Teacher.description(
                        criterion = criterion
                    )
                ),
                Message(
                    role = Role.user.name,
                    text = questionToAnswerPrompt
                )
            )
        )
        val promptResponse = api.yandex.sendPromptRequest(prompt)
        responses.add(promptResponse).also { logger.log(Level.INFO, promptResponse) }
        getAssessmentFromPromptResponse(promptResponse).also {
            if (it != -1) {
                assessments.add(it)
            }
        }
        // TODO: Do with this something
        Thread.sleep(1000) // using thread sleep, to not look like spammer to YaGPT services
    }

    logger.log(Level.INFO, responses.toString())
    logger.log(Level.INFO, assessments.toString())

    val totalAssessment = assessments.sum().toFloat() / assessments.size
    logger.log(Level.INFO, totalAssessment.toString())
    return totalAssessment
}
