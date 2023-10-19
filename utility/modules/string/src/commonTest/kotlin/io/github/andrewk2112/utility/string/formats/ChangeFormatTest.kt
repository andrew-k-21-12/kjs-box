package io.github.andrewk2112.utility.string.formats

import io.github.andrewk2112.utility.string.formats.cases.*
import io.github.andrewk2112.utility.string.formats.other.PackageName
import io.github.andrewk2112.utility.string.formats.other.UniversalPath
import kotlin.test.Test
import kotlin.test.assertEquals

class ChangeFormatTest {

    @Test
    fun testChangeFormat() {
        assertEquals(
            "com.package.projectgroup",
            "com.package.project-group".changeFormat(PackageName, PackageName)
        )
        assertEquals(
            "com/package/project-group",
            "com.package.project-group".changeFormat(PackageName, UniversalPath)
        )
        assertEquals(
            "com.package.projectgroup",
            "com/package/project-group".changeFormat(UniversalPath, PackageName)
        )
    }

    @Test
    fun testCombinedChangeFormat() {
        assertEquals(
            "oneTwoThree",
            "one.two.three".changeFormat(PackageName, UniversalPath, to = LowerCamelCase)
        )
        assertEquals(
            "oneTwoThree",
            "one/two/three".changeFormat(PackageName, UniversalPath, to = LowerCamelCase)
        )
        assertEquals(
            "one.two.three",
            "ONE-TWO_THREE".changeFormat(KebabCase, SnakeCase, to = PackageName)
        )
        assertEquals(
            "oneTwoThreefour",
            "one-two_threeFour".changeFormat(KebabCase, SnakeCase, to = LowerCamelCase)
        )
        assertEquals(
            "oneTwoThree",
            "one_twoThree".changeFormat(AnyCase, to = LowerCamelCase)
        )
        assertEquals(
            "oneTwoThree",
            "One_two-Three".changeFormat(AnyCase, to = LowerCamelCase)
        )
        assertEquals(
            "OneTwoThree",
            "oneTwoThree".changeFormat(AnyCase, to = CamelCase)
        )
    }

}
