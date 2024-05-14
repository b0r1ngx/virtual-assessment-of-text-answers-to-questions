package utils

import java.io.File

internal fun getStringFromJsonFile(fileName: String): String {
    var promptResponse = ""
    File("./src/main/resources/${fileName}")
        .readLines(charset = Charsets.UTF_8)
        .forEach { promptResponse += it.trim() }
    return promptResponse
}