package dev.boringx.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.serializer

private val json = Json

internal inline fun <reified T> T.encodeToString(): String =
    json.encodeToString(serializersModule.serializer(), this)

internal inline fun <reified T> String.decodeFromStringSafety(): T? =
    try {
        json.decodeFromString<T>(this)
    } catch (e: Exception) {
        null
    }
