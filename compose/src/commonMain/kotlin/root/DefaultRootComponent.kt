package root

import Course
import Question
import SqlDriverFactory
import Test
import UserViewModel
import client.Repository
import client.network.ClientApi
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import createDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import model.UserType
import screens.auth.AuthViewModel
import screens.test.TestViewModel
import screens.tests.TestsViewModel
import users.User
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class DefaultRootComponent(
    sqlDriverFactory: SqlDriverFactory,
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val database = createDatabase(sqlDriverFactory = sqlDriverFactory)
    private val clientApi = ClientApi()
    private val repository = Repository(database = database, api = clientApi)
    override val userViewModel = UserViewModel(repository = repository)

    // TODO: if we doesn't know about user, navigate him to Auth
    //  to solve it without app lag, we can create splash screen
    private val navigation = StackNavigation<Config>()
    override val navigationStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Tests,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            Config.Auth -> RootComponent.Child.Auth(authComponent(componentContext))
            Config.Test -> RootComponent.Child.Test(testComponent(componentContext))
            Config.Tests -> RootComponent.Child.Tests(testsComponent(componentContext))
        }

    private fun authComponent(componentContext: ComponentContext): AuthViewModel =
        AuthViewModel(
            componentContext = componentContext,
            mainCoroutineContext = Dispatchers.Main.immediate,
            repository = repository,
//            onShowWelcome = { navigation.push(Config.Welcome) },
        )

    private fun testComponent(componentContext: ComponentContext): TestViewModel =
        TestViewModel(
            componentContext = componentContext,
            mainCoroutineContext = Dispatchers.Main.immediate,
            test = Test( // mock, later delete
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
//            onFinished = navigation::pop,
        )

    private fun testsComponent(componentContext: ComponentContext): TestsViewModel =
        TestsViewModel(
            componentContext = componentContext,
            mainCoroutineContext = Dispatchers.Main.immediate,
            repository = repository,
        )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    init {
        lifecycle // Access the Lifecycle
        stateKeeper // Access the StateKeeper
        instanceKeeper // Access the InstanceKeeper
        backHandler // Access the BackHandler
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config

        @Serializable
        data object Test : Config

        @Serializable
        data object Tests : Config
    }
}
