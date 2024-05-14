package utils

import dotenv
import model.EnvironmentVariables

fun createModelUri(
    folderId: String = dotenv[
        EnvironmentVariables.FOLDER_ID.name
    ]
) = "gpt://$folderId/yandexgpt-lite"
