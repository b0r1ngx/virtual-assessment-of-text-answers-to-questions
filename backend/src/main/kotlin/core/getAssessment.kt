package core

import Answer
import Criterion
import Question
import api.yandex.sendPromptRequest
import core.formatter.getAssessmentFromPromptResponse
import logger.logger
import model.ContextType
import model.prompt.request.CompletionOptions
import model.prompt.request.Message
import model.prompt.request.PromptRequest
import model.prompt.request.Role
import utils.createModelUri
import utils.preparePrompt
import utils.round
import java.util.logging.Level

fun Answer.getAssessment(
    question: Question,
    criteria: List<Criterion> = Criterion.entries,
    modelUri: String = createModelUri(),
    completionOptions: CompletionOptions = CompletionOptions.default
): Pair<List<Triple<Criterion, Int, String>>, Double> {
    val responses = mutableListOf<String>()
    val assessments = mutableListOf<Int>()
    val criterionToMarkWithResponse = mutableListOf<Triple<Criterion, Int, String>>()

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
        val promptResponse = sendPromptRequest(prompt)
        responses.add(promptResponse).also { logger.log(Level.INFO, promptResponse) }
        getAssessmentFromPromptResponse(promptResponse).also {
            if (it != -1) {
                assessments.add(it)
                criterionToMarkWithResponse.add(Triple(criterion, it, promptResponse))
            }
        }
        // TODO: Do with this something
        //  using thread sleep, to not look like spammer to YaGPT services
        //  {"error":{"grpcCode":8,"httpCode":429,"message":"ai.textGenerationCompletionSessionsCount.count gauge quota limit exceed: allowed 1 requests","httpStatus":"Too Many Requests","details":[]}}
        Thread.sleep(300)
    }

    logger.log(Level.INFO, responses.toString())
    logger.log(Level.INFO, assessments.toString())

    val averageAssessment = assessments.average().round()
    logger.log(Level.INFO, averageAssessment.toString())

    return criterionToMarkWithResponse to averageAssessment
}
