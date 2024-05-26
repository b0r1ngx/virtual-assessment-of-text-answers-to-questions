package core

import Repository
import TestAnswers
import TestAssessments

fun assess(repository: Repository, testAnswers: TestAnswers) {
    val testAssessments = mutableListOf<TestAssessments>()

    for ((question, answer) in testAnswers.questionsToAnswers) {
        val (criteriaToMarkWithResponse, avgMark) = answer.getAssessment(question)
        testAssessments.add(
            TestAssessments(
                testId = testAnswers.testId,
                userEmail = testAnswers.userEmail,
                questionId = question.id,
                answer = answer,
                avgMarkAi = avgMark,
                criteriaToMarkWithResponse = criteriaToMarkWithResponse
            )
        )
    }

    repository.saveAssessment(testAssessments = testAssessments)
}
