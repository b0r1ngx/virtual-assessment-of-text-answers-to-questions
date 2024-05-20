package screens.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.toHumanReadable(): String {
    with(toLocalDateTime()) {
        return "$hour:$minute, $dayOfMonth.$monthNumber.$year"
    }
}

fun Instant.toLocalDateTime() =
    toLocalDateTime(TimeZone.currentSystemDefault())
