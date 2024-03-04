package dev.boringx.model

// TODO: Make it like sealed class, where only Teacher, has description?
enum class ContextType {
    None,
    Teacher;

    fun description(criterion: Criterion) =
        "Ты - опытный специалист, профессор в сфере информационных технологий. " +
                "Оцени ответ на вопрос по критерию ${criterion.text}. " +
                "В начале своего ответа, дай оценку по десятибалльной шкале"
}
