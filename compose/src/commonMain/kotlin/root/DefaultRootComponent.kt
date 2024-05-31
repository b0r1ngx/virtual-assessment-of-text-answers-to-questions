package root

import ClientApi
import ClientRepository
import SqlDriverFactory
import TestModel
import UserViewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import createDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import model.UserType
import screens.auth.AuthViewModel
import screens.test.EditingTestViewModel
import screens.test.PassingTestViewModel
import screens.test.ResultTestViewModel
import screens.tests.TestsViewModel

class DefaultRootComponent(
    sqlDriverFactory: SqlDriverFactory,
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val database = createDatabase(sqlDriverFactory = sqlDriverFactory)
    private val clientApi = ClientApi()
    private val repository = ClientRepository(database = database, api = clientApi)
    private val user = repository.getUserSelf()
    private val initialConfiguration = if (user == null) Config.Auth else Config.Tests

    private val navigation = StackNavigation<Config>()
    override val navigationStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = initialConfiguration,
            handleBackButton = true,
            childFactory = ::child,
        )

    override lateinit var userViewModel: UserViewModel

    init {
        if (user != null) {
            userViewModel = UserViewModel(user = user, repository = repository)
        }
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Auth -> RootComponent.Child.Auth(authComponent(componentContext))
            is Config.Tests -> RootComponent.Child.Tests(testsComponent(componentContext))
            is Config.EditingTest -> RootComponent.Child.EditingTest(
                editingTestComponent(componentContext, config)
            )

            is Config.PassingTest -> RootComponent.Child.PassingTest(
                passingTestComponent(componentContext, config)
            )

            is Config.ResultTest -> RootComponent.Child.ResultTest(resultTestComponent(config))
        }

    private fun authComponent(
        componentContext: ComponentContext
    ) = AuthViewModel(
        componentContext = componentContext,
        mainCoroutineContext = Dispatchers.Main.immediate,
        repository = repository,
        onRegister = { userModel ->
            userViewModel = UserViewModel(user = userModel, repository = repository)
            navigation.replaceCurrent(Config.Tests)
        },
    )

    private fun testsComponent(
        componentContext: ComponentContext,
    ) = TestsViewModel(
        componentContext = componentContext,
        mainCoroutineContext = Dispatchers.Main.immediate,
        repository = repository,
        onCreateTestClick = { navigation.push(Config.EditingTest()) },
        onTestClick = { test ->
            if (userViewModel.user.typeId == UserType.Student.ordinal)
            // todo: if test is assessed by teacher, navigate to ResultTest
            //  should we write this logic here?
                navigation.push(Config.PassingTest(test = test))
            else
                navigation.push(Config.EditingTest(test = test))
        }
    )

    private fun editingTestComponent(
        componentContext: ComponentContext,
        config: Config.EditingTest
    ) = EditingTestViewModel(
        componentContext = componentContext,
        mainCoroutineContext = Dispatchers.Main.immediate,
        repository = repository,
        test = config.test,
        onFinished = navigation::pop,
    )

    private fun passingTestComponent(
        componentContext: ComponentContext,
        config: Config.PassingTest
    ) = PassingTestViewModel(
        componentContext = componentContext,
        mainCoroutineContext = Dispatchers.Main.immediate,
        repository = repository,
        test = config.test,
        onFinished = navigation::pop,
    )

    private fun resultTestComponent(
        config: Config.ResultTest
    ) = ResultTestViewModel(
        mainCoroutineContext = Dispatchers.Main.immediate,
        repository = repository,
        test = config.test,
        onFinished = navigation::pop,
    )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config

        @Serializable
        data object Tests : Config

        @Serializable
        data class EditingTest(val test: TestModel? = null) : Config

        @Serializable
        data class PassingTest(val test: TestModel) : Config

        @Serializable
        data class ResultTest(val test: TestModel) : Config
    }
}
