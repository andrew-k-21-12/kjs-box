package io.github.andrewk2112.templates

import java.io.IOException
import java.io.InputStream
import java.util.*

/**
 * Does pretty primitive template inflation by just loading a raw template from the resources
 * and inserting simple values into it.
 */
internal class SimpleTemplatesInflater {

    // API.

    /**
     * Inflates a template from the resources by the [templateName] and inserts the provided [args] into it.
     */
    @Throws(IOException::class, IllegalFormatException::class)
    internal fun inflate(templateName: String, vararg args: Any): String =
        cache
            .getOrPut(templateName) { readTemplateFromResources(templateName) }
            .format(*args)



    // Private.

    /**
     * Reads a template by the [templateName] from the resources.
     */
    @Throws(IOException::class)
    private fun readTemplateFromResources(templateName: String): String =
        this::class.java.getResourceAsStream(templateName)
                       ?.use(InputStream::readBytes)
                       ?.let { String(it) } ?: throw IOException("Can not open the requested template: $templateName")

    /** To avoid memory abuses and inflate faster. */
    private val cache = mutableMapOf<String, String>()

}
