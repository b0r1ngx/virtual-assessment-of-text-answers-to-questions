data class TestAssessments(
    val answer: Answer,
    val avgMarkAi: Double,
    val criterionToMarkWithResponse: List<Triple<Criterion, Int, String>>,
)
