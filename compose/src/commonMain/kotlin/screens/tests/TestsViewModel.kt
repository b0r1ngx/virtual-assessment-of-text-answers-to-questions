package screens.tests

import AppViewModel
import ClientRepository
import Course
import Question
import TestModel
import UserModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import model.UiState
import model.UserType
import kotlin.coroutines.CoroutineContext
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private val mockTelecomQuestions = listOf(
    Question(text = "Как происходит увеличение числа операций умножения с ростом порядка рекурсивного фильтра"),
    Question(text = "Что такое медианный фильтр? Какие у него свойства?"),
)

private val mockArchitectureQuestions = listOf(
    Question(text = "Общие сведения об управлении потребляемой мощностью в микроконтроллере"),
    Question(text = "Основные классификационные признаки микропроцессорной системы"),
)

private val mockOSBQuestions = listOf(
    Question(text = "Продолжите фразу. Операционная система – это …"), // виртуальная машина
    Question(text = "Продолжите фразу. Операционная система – это  …"), // реактивная система
    Question(text = "Продолжите фразу. Операционная система – это система управления …"), // ресурсами
    Question(text = "Продолжите фразу. Процесс в операционной системе – это …"), // выполняющаяся программа
    Question(text = "Какой атрибут файла в операционной системе является основным ?"), // имя
    Question(text = "Какой атрибут процесса в операционной системе является основным ?"), // идентификатор
    Question(text = "Какой тип отношения между управляемыми объектами операционной системы формирует пользователь операционной системы ?"), // Процесс – Ресурс
    Question(text = "Какое ядро имеет операционная система Microsoft Windows 10 ?"), // Microsoft Windows NT
    Question(text = "Какое ядро имеет операционная система Ubuntu 16.04 LTS Server ?"), // Linux
    Question(text = "Что происходит при выгрузке операционной системы из оперативной памяти компьютера ?"), // Закрытие (с сохранением вне оперативной памяти) всех открытых файлов и завершение всех выполняющихся процессов операционной системы
    Question(text = "Перечислите компоненты, которые входят в состав автоматизированной системы решения прикладных задач на компьютере ?"), // Операционная система, Пользователь, Вычислительная машина, Системная программа, Прикладная программа
    Question(text = "На каких этапах жизненного цикла процесса операционной системы ему требуется сегмент стека ?"), // Только на этапах возникновения прерываний выполнения процесса
)

private val mockTests = listOf(
    TestModel(
        creator = UserModel(
            typeId = UserType.Teacher.ordinal,
            name = "Лупин Анатолий Викторович",
            email = "lupin.av@edu.spbstu.ru"
        ),
        name = "Промежуточное тестирование",
        course = Course(id = 1, name = "Цифровая обработка сигналов"),
        start_at = Clock.System.now().minus(2.toDuration(DurationUnit.HOURS)),
        end_at = Clock.System.now().minus(1.toDuration(DurationUnit.HOURS)),
        questions = mockTelecomQuestions
    ),
    TestModel(
        creator = UserModel(
            typeId = UserType.Teacher.ordinal,
            name = "Богач Наталья Владимировна",
            email = "bogach.nv@edu.spbstu.ru"
        ),
        name = "Промежуточное тестирование",
        course = Course(id = 2, name = "Телекоммуникационные технологии"),
        start_at = Clock.System.now(),
        end_at = Clock.System.now().plus(1.toDuration(DurationUnit.HOURS)),
        questions = mockTelecomQuestions
    ),
    TestModel(
        creator = UserModel(
            typeId = UserType.Teacher.ordinal,
            name = "Тарасов Олег Михайлович",
            email = "tarasov.om@edu.spbstu.ru"
        ),
        name = "Экзамен",
        course = Course(id = 3, name = "Архитектура ЭВМ"),
        start_at = Clock.System.now(),
        end_at = Clock.System.now().plus(2.toDuration(DurationUnit.HOURS)),
        questions = mockArchitectureQuestions
    ),
    TestModel(
        creator = UserModel(
            typeId = UserType.Teacher.ordinal,
            name = "Малышев Игорь Алексеевич",
            email = "malyshev.ia@edu.spbstu.ru"
        ),
        name = "Итоговое тестирование",
        course = Course(id = 4, name = "Основы операционных систем"),
        start_at = Clock.System.now(),
        end_at = Clock.System.now().plus(1.toDuration(DurationUnit.DAYS)),
        questions = mockOSBQuestions
    ),
)

class TestsViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    val onCreateTestClick: () -> Unit,
    val onTestClick: (test: TestModel) -> Unit
) : AppViewModel, ComponentContext by componentContext {

    private val scope = coroutineScope(mainCoroutineContext + SupervisorJob())

    private val _uiState = MutableStateFlow(UiState())
    override val uiState = _uiState.asStateFlow()

    // left open the problem of collecting state not depends on lifecycle,
    // what this approach differs of using Decompose Value
    private val _tests: MutableStateFlow<List<TestModel>> = MutableStateFlow(listOf())
    val tests: StateFlow<List<TestModel>> = _tests.asStateFlow()

    init {
        scope.launch {
            _tests.update { repository.getTests() } // mockTests
        }
    }

}
