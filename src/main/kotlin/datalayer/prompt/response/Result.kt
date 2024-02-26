package dev.boringx.datalayer.prompt.response

data class Result(
    val alternatives: List<Alternative>,
    val usage: Usage,
    val modelVersion: String
)
