interface Platform {
    val platform: Platforms
    val name: String
}

enum class Platforms {
    android, ios, desktop
}

expect fun getPlatform(): Platform