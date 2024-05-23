package root

import SqlDriverFactory
import Test
import UserViewModel
import client.ClientRepository
import client.network.ClientApi
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import createDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
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
            is Config.Auth -> RootComponent.Child.Auth(authComponent(componentContext))
            is Config.Tests -> RootComponent.Child.Tests(testsComponent(componentContext))
            is Config.EditingTest -> RootComponent.Child.EditingTest(
                editingTestComponent(componentContext, config)
            )

            is Config.PassingTest -> RootComponent.Child.PassingTest(
                passingTestComponent(componentContext, config)
            )

            is Config.ResultTest -> RootComponent.Child.ResultTest(
                resultTestComponent(config)
            )
        }

    private fun authComponent(
        componentContext: ComponentContext
    ) = AuthViewModel(
        componentContext = componentContext,
        mainCoroutineContext = Dispatchers.Main.immediate,
        repository = repository,
//            onShowWelcome = { navigation.push(Config.Welcome) },
    )

    private fun testsComponent(
        componentContext: ComponentContext,
    ) = TestsViewModel(
        componentContext = componentContext,
        mainCoroutineContext = Dispatchers.Main.immediate,
        repository = repository,
        onTestClick = { test ->
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
        data object Tests : Config

        @Serializable
        data class EditingTest(val test: Test? = null) : Config

        @Serializable
        data class PassingTest(val test: Test) : Config

        @Serializable
        data class ResultTest(val test: Test) : Config
    }
}
