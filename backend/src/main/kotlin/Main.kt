import dev.boringx.core.getAssessment
import dev.boringx.datalayer.repository.Repository

// TODO: When start implement Client (mobile app) - think about logic that we can share to Backend
fun main() {
    val repository = Repository()
    val assessments = mutableListOf<Float>()
    val questionsToAnswers = repository.getAllQuestionsToAnswers()
    for ((question, answer) in questionsToAnswers) {
        assessments.add(answer.getAssessment(question))
    }
    println("Assessments: $assessments")
}
