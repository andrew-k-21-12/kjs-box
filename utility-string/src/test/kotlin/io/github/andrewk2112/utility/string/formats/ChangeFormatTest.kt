package io.github.andrewk2112.utility.string.formats

import kotlin.test.Test
import kotlin.test.assertEquals

class ChangeFormatTest {

    @Test
    fun testChangeFormat() {
        assertEquals(
            "com.package.projectgroup",
            "com.package.project-group".changeFormat(ProjectGroupFormat, PackageFormat)
        )
        assertEquals(
            "com.package.project",
            "com.package.project".changeFormat(PackageFormat, PackageFormat)
        )
        assertEquals(
            "com/package/project-group",
            "com.package.project-group".changeFormat(ProjectGroupFormat, PathFormat)
        )
        assertEquals(
            "com.package.projectgroup",
            "com/package/project-group".changeFormat(PathFormat, PackageFormat)
        )
    }

}
