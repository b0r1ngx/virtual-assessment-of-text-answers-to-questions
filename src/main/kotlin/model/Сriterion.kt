package dev.boringx.model

enum class Criterion(
    val text: String
) {
    Correctness("правильности"),
    Brevity("краткости"),
    Style("стиля"),
    Coherence("связности ответа"),
}
