package io.github.andrewk2112.utility.gradle.extensions

import org.apache.tools.ant.taskdefs.condition.Os
import java.io.File

/**
 * Replaces the default system [File.separatorChar]s when running on [Os.FAMILY_WINDOWS]
 * with the conventional and working everywhere "/".
 */
fun String.toUniversalPathString(): String =
    if (Os.isFamily(Os.FAMILY_WINDOWS)) replace(File.separatorChar, '/') else this
