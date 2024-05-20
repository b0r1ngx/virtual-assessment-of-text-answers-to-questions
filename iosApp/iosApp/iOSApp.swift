import SwiftUI
import Compose

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

	var body: some Scene {
		WindowGroup {
			ComposeView(root: appDelegate.root)
			    .ignoresSafeArea(.all)
		}
	}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    let root: RootComponent = DefaultRootComponent(
        sqlDriverFactory: CommonSqlDriverFactory(),
        componentContext: DefaultComponentContext(lifecycle: ApplicationLifecycle())
    )
}
