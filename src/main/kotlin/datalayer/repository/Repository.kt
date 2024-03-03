package dev.boringx.datalayer.repository

import dev.boringx.datalayer.Answer
import dev.boringx.datalayer.Question

class Repository {
    private val bank = mapOf(
        Question(
            text = "Как происходит увеличение числа операций умножения с ростом порядка рекурсивного фильтра"
        ) to Answer(
            text = "Линейно"
        ),
        Question(
            text = "Что такое медианный фильтр? Какие у него свойства?"
        ) to Answer(
            text = "Медианным фильтром локально обрабатываются отсчеты в скользящем окне, которая включает в себя конечное число отсчетов сигнала. Для каждого из положений окна выделенные в нем отсчеты распределяются в порядке возрастания или убывания их значений. В отсортированном наборе значения сигнала среднее значение будет являться медианной для рассматриваемого положения окна."
        )
    )

    fun getPrompt(): String {
        // bank.entries.shuffled().first()
        val questionAndAnswer =
            bank.entries.first()
        return "Вопрос: ${questionAndAnswer.key.text}." +
                "Ответ: ${questionAndAnswer.value.text}."
    }
}