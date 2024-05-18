package screens.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.toHumanReadable(): String {
    with(toLocalDateTime(TimeZone.currentSystemDefault())) {
        return "$hour:$minute, $dayOfMonth.$monthNumber.$year"
    }
}
