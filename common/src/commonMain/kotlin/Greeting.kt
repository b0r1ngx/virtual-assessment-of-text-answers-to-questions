class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello ${platform.name}, from Compose Multiplatform!"
    }
}