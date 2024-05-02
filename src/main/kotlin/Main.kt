package dev.boringx

import dev.boringx.datalayer.repository.Repository
import dev.boringx.core.getAssessment

// TODO: When start implement Client (mobile app) - think about logic that we can share to Backend
fun main() {
    val repository = Repository()
    val assessments = mutableListOf<Float>()
    val questionsToAnswers = repository.getAllQuestionsToAnswers()
    for ((question, answer) in questionsToAnswers) {
        assessments.add(getAssessment(question, answer))
    }
    println("Assessments: $assessments")
}
