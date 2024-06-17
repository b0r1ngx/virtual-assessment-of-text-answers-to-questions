package utils

// TODO: instead of using round at UI layer, round one time, when saving to database?
fun Double.round(decimals: Int = 1): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}
