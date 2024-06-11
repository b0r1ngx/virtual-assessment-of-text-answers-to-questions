import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Long = -1,
    val text: String,
    val avgMarkAi: Double = -1.0,
)
