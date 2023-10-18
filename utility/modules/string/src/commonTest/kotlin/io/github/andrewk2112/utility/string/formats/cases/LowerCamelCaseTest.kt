package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.formats.cases.LowerCamelCase.joinWords
import io.github.andrewk2112.utility.string.formats.cases.LowerCamelCase.postProcess
import kotlin.test.Test
import kotlin.test.assertEquals

class LowerCamelCaseTest {

    @Test
    fun testJoinWordsAndPostProcess() =
        assertEquals(
            "fooBazBar",
            postProcess(joinWords(listOf("Foo", "Baz", "Bar")))
        )

}
