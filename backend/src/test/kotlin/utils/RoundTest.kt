package utils

import org.junit.Test
import kotlin.test.assertEquals

class RoundTest {

    @Test
    fun roundDown() {
        val notFormattedNumber = 7.1477
        val expected = 7.1
        val actual = notFormattedNumber.round()
        assertEquals(expected, actual)
    }

    @Test
    fun roundUp() {
        val notFormattedNumber = 7.1512
        val expected = 7.2
        val actual = notFormattedNumber.round()
        assertEquals(expected, actual)
    }

}
