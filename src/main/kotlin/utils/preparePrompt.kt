package dev.boringx.utils

import dev.boringx.model.Answer
import dev.boringx.model.Question

fun preparePrompt(question: Question, answer: Answer): String =
    "Вопрос: ${question.text}. Ответ: ${answer.text}."
