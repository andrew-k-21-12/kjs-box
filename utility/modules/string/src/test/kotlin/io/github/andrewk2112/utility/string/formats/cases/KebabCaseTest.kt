package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.formats.cases.KebabCase.extractWords
import io.github.andrewk2112.utility.string.formats.cases.KebabCase.joinWords
import kotlin.test.Test
import kotlin.test.assertEquals

class KebabCaseTest {

    @Test
    fun testExtractWords() =
        assertEquals(
            listOf("Foo", "Baz", "Bar"),
            extractWords("Foo-Baz-Bar")
        )

    @Test
    fun testJoinWords() =
        assertEquals(
            "foo-baz-bar",
            joinWords(listOf("Foo", "Baz", "Bar"))
        )

}
