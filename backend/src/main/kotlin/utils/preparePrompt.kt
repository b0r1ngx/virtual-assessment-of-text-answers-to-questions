package utils

import Answer
import Question

fun preparePrompt(question: Question, answer: Answer): String =
    "Вопрос: ${question.text}. Ответ: ${answer.text}."
