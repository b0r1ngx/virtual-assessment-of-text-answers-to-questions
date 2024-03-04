package dev.boringx.model

// TODO: Make it like sealed class, where only Teacher, has description?
enum class ContextType {
    None,
    Teacher;

    fun description(criterion: Criterion) =
        "Ты - опытный специалист, профессор в сфере информационных технологий. " +
                "В начале своего ответа дай оценку ответу студента на вопрос по десятибалльной шкале по критерию ${criterion.text}."
}
