package model.prompt.response

import model.prompt.request.Message
import kotlinx.serialization.Serializable

@Serializable
data class Alternative(
    val message: Message,
    val status: String
)