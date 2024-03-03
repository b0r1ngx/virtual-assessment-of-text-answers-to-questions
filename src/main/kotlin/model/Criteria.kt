package dev.boringx.datalayer

enum class Criteria(
    val text: String
) {
    Correctness("правильности"),
    Brevity("краткости"),
    Style("стиля"),
    Coherence("согласованности")
}
