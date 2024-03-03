package dev.boringx.model

// TODO: Make it like sealed class, where only Teacher, has description?
enum class ContextType {
    None,
    Teacher;

    fun description(criteria: Criteria) =
        "Ты - опытный специалист, профессор в сфере информационных технологий." +
                "Оцени ответ студента на вопрос по десятибалльной шкале по критерию ${criteria.text} ответа на вопрос"
}
