package io.github.andrewk2112.versioncatalogsgenerator.gradle

import org.gradle.api.provider.Property
import java.io.File

/**
 * A version catalog to be processed.
 */
interface VersionCatalog {

    /**
     * This read-only property is a Gradle's requirement.
     * Used as a file and class name for the corresponding generated wrapper.
     */
    val name: String

    /** A catalog file to generate a wrapper for. */
    val file: Property<File>

}
