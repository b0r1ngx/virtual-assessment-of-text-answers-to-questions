import kotlinx.datetime.DateTimeUnit

data class Test(
    val name: String,
//    val teacher: Teacher,
//    val course: Course,
    val start_at: DateTimeUnit,
    val end_at: DateTimeUnit,
    val question: List<Question>
)