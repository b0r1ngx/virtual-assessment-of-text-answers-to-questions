package core

import Repository
import TestAnswers
import TestAssessments
import logger.logger
import java.util.logging.Level

fun assessAnswerAndSaveToDatabase(repository: Repository, testAnswers: TestAnswers) {
    val testAssessments = mutableListOf<TestAssessments>()

    for ((question, answer) in testAnswers.questionsToAnswers) {
        val (criteriaToMarkWithResponse, avgMark) = answer.getAssessment(question)
        testAssessments.add(TestAssessments(answer, avgMark, criteriaToMarkWithResponse))
    }

    repository.saveAssessment(testAssessments = testAssessments)
    logger.log(Level.INFO, "successfully save to database")
    repository.checkAssessments()
}