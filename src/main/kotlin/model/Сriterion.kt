package dev.boringx.model

/* TODO: Рассмотреть возможность добавления дополнительных критериев?
 *  1. Степень осознанности, понимания изученного, (это можно понять из суммы используемых критериев)
 *  2. Грамотность, (мало важен)
 *  3. Языковое оформление ответа (мало важен)
 *
 *  TODO: Choose criteria dynamically?
 *   1. What field of question?
 *   2. Analyze answer? (how long it is, etc..)
 */
enum class Criterion(
    val text: String
) {
    Correctness("правильности"),
    Brevity("краткости"),
    Style("стиля"),
    Completeness("полнота"),
    Coherence("связности ответа"),
}
