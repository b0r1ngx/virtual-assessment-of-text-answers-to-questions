package screens.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

// TODO: to use it, get user TimeZone
// TODO: delete default timezone?
fun Instant.toHumanReadable(timeZone: TimeZone = TimeZone.UTC): String {
    with(toLocalDateTime(timeZone)) {
        return "$hour:$minute, $dayOfMonth.$monthNumber.$year"
    }
}