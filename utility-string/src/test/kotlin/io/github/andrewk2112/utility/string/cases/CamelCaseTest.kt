package io.github.andrewk2112.utility.string.cases

import io.github.andrewk2112.utility.string.cases.CamelCase.extractWords
import io.github.andrewk2112.utility.string.cases.CamelCase.joinWords
import kotlin.test.Test
import kotlin.test.assertEquals

class CamelCaseTest {

    @Test
    fun testExtractWords() {
        assertEquals(
            listOf("foo", "Baz", "Bar"),
            extractWords("fooBazBar")
        )
        assertEquals(
            listOf("Foo", "Baz", "Bar"),
            extractWords("FooBazBar")
        )
    }

    @Test
    fun testJoinWords() {
        assertEquals(
            "FooBazBar",
            joinWords(listOf("Foo", "Baz", "Bar"))
        )
        assertEquals(
            "FooBazBar",
            joinWords(listOf("foo", "BAZ", "bAR"))
        )
    }

}
