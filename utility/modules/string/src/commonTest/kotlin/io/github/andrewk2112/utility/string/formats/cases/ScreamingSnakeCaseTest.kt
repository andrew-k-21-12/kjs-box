package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.formats.cases.ScreamingSnakeCase.joinWords
import kotlin.test.Test
import kotlin.test.assertEquals

class ScreamingSnakeCaseTest {

    @Test
    fun testJoinWords() {
        assertEquals(
            "FOO_BAZ_BAR",
            joinWords(listOf("Foo", "Baz", "Bar"))
        )
    }

}
