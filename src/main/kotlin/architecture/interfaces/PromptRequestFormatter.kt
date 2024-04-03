package dev.boringx.architecture.interfaces

interface PromptRequestFormatter {
    fun format(request: String): String
}