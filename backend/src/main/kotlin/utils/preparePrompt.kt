package utils

import model.Answer
import model.Question

fun preparePrompt(question: Question, answer: Answer): String =
    "Вопрос: ${question.text}. Ответ: ${answer.text}."
