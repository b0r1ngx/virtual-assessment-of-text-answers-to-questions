import core.getAssessment
import datalayer.repository.Repository

// TODO: When start implement Client (mobile app) - think about logic that we can share to Backend
fun main() {
    val repository = Repository()
    val assessments = mutableListOf<Double>()
    val questionsToAnswers = repository.getAllQuestionsToAnswers()
    for ((question, answer) in questionsToAnswers) {
        assessments.add(answer.getAssessment(question).second)
    }
    println("Assessments: $assessments")
}
