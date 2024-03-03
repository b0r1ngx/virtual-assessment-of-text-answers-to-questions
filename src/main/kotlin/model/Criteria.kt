package dev.boringx.model

enum class Criteria(
    val text: String
) {
    Correctness("правильности"),
    Brevity("краткости"),
    Style("стиля"),
    Coherence("согласованности")
}
