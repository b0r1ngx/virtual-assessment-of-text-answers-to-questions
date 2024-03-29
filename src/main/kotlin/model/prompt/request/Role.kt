package dev.boringx.model.prompt.request

import dev.boringx.model.ContextType

enum class Role(
    val types: List<ContextType> = listOf()
) {
    user, // предназначена для отправки пользовательских сообщений к модели.
    system(
        listOf(
            ContextType.Teacher
        )
    ), // позволяет задать контекст запроса и определить поведение модели.
    assistant // используется для ответов, которые генерирует модель.
}
