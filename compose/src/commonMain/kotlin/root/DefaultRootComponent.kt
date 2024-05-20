package root

import com.arkivanov.decompose.ComponentContext

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    init {
        lifecycle // Access the Lifecycle
        stateKeeper // Access the StateKeeper
        instanceKeeper // Access the InstanceKeeper
        backHandler // Access the BackHandler
    }
}