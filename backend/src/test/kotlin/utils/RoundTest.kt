package utils

import org.junit.Test
import kotlin.test.assertEquals

class RoundTest {

    @Test
    fun round_down() {
        val notFormattedNumber = 7.1477
        val expected = 7.1
        val actual = notFormattedNumber.round(1)
        assertEquals(expected, actual)
    }

    @Test
    fun round_up() {
        val notFormattedNumber = 7.1512
        val expected = 7.2
        val actual = notFormattedNumber.round(1)
        assertEquals(expected, actual)
    }

}
