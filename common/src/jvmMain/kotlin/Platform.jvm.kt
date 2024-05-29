// TODO: Extract logic that needed for client from common module, dele this
class DesktopPlatform: Platform {
    override val platform: Platforms = Platforms.desktop
    override val name: String = Platforms.desktop.name
}

actual fun getPlatform(): Platform = DesktopPlatform()