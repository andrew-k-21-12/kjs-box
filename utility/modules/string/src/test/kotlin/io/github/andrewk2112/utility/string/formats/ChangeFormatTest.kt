package io.github.andrewk2112.utility.string.formats

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

}
