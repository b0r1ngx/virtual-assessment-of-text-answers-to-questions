import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import client.Repository
import client.network.ClientApi
import kotlinx.datetime.Clock
import model.UserType
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.auth.AuthViewModel
import screens.test.TestScreen
import screens.test.TestViewModel
import screens.tests.TestsViewModel
import users.User
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
@Preview
fun App(sqlDriverFactory: SqlDriverFactory) {
    val repository = Repository(
        database = createDatabase(sqlDriverFactory),
        api = ClientApi()
    )
    val userViewModel = UserViewModel(repository)
    val authViewModel = AuthViewModel(repository) // provide it with userViewModel::setUser
    val testsViewModel = TestsViewModel(repository)
    val testViewModel = TestViewModel(
        test = Test(
            creator = User(
                type = UserType.Teacher.ordinal,
                name = "Лупин Анатолий Викторович",
                email = "lupin.av@edu.spbstu.ru"
            ),
            name = "Промежуточное тестирование",
            course = Course(name = "Цифровая обработка сигналов"),
            start_at = Clock.System.now().minus(2.toDuration(DurationUnit.HOURS)),
            end_at = Clock.System.now().minus(1.toDuration(DurationUnit.HOURS)),
            questions = listOf(
                Question(text = "Какое ядро имеет операционная система Microsoft Windows 10 ?"),
                Question(text = "Что происходит при выгрузке операционной системы из оперативной памяти компьютера ?")
            )
        ),
    )

    MaterialTheme {
//        AuthScreen(authViewModel)
//        TestsScreen(userViewModel, testsViewModel)
        TestScreen(userViewModel, testViewModel)
    }
}
