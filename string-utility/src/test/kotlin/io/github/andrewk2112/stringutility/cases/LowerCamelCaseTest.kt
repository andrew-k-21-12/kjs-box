package io.github.andrewk2112.stringutility.cases

import io.github.andrewk2112.stringutility.cases.LowerCamelCase.joinWords
import kotlin.test.Test
import kotlin.test.assertEquals

class LowerCamelCaseTest {

    @Test
    fun testJoinWords() =
        assertEquals(
            "fooBazBar",
            joinWords(listOf("Foo", "Baz", "Bar"))
        )

}
