package dev.boringx.datalayer.prompt.response

data class Error(
    val grpcCode: Int,
    val httpCode: Int,
    val message: String,
    val httpStatus: String,
    val details: List<String>
)
