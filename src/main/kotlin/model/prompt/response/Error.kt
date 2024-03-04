package dev.boringx.model.prompt.response

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val grpcCode: Int,
    val httpCode: Int,
    val message: String,
    val httpStatus: String,
    val details: List<String>
)
