data class TestAssessments(
    val answer: Answer,
    val avgMarkAi: Double,
    val criteriaToMarkWithResponse: List<Triple<Criterion, Int, String>>,
)
