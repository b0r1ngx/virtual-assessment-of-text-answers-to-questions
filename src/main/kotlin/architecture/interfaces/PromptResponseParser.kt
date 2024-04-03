package dev.boringx.architecture.interfaces

interface PromptResponseParser {
    fun parse(response: String): String
}