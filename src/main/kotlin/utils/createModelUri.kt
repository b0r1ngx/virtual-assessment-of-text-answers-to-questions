package dev.boringx.utils

import dev.boringx.datalayer.EnvironmentVariables
import dev.boringx.dotenv

fun createModelUri(
    folderId: String = dotenv[
        EnvironmentVariables.FOLDER_ID.name
    ]
) = "gpt://$folderId/yandexgpt-lite"
