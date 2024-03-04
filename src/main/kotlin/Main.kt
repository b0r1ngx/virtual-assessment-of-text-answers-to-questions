package dev.boringx

import dev.boringx.datalayer.repository.Repository
import dev.boringx.utils.getEstimation

fun main() {
    val repository = Repository()
    val estimations = mutableListOf<Float>()
    val questionsToAnswers = repository.getAllQuestionsToAnswers()
    for ((question, answer) in questionsToAnswers) {
        estimations.add(getEstimation(question, answer))
    }
    println("Estimations: $estimations")
}
