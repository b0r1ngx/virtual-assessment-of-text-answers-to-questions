package dev.boringx

import dev.boringx.datalayer.repository.Repository
import dev.boringx.utils.getAssessment

fun main() {
    val repository = Repository()
    val assessments = mutableListOf<Float>()
    val questionsToAnswers = repository.getAllQuestionsToAnswers()
    for ((question, answer) in questionsToAnswers) {
        assessments.add(getAssessment(question, answer))
    }
    println("Assessments: $assessments")
}
