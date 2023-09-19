package io.github.andrewk2112.versioncatalogsgenerator.codegenerators



// Public.

/**
 * Injects code emitters for blocks dependent on [T].
 */
internal interface CodeEmitters<T> {

    /**
     * Registers a code emitter returning its metadata (string template pointer).
     */
    fun emitCode(codeEmitter: (T) -> String): String

}

/**
 * Provides common means of code generation.
 */
internal class CommonCodeGeneration {

    /**
     * Returns code blocks described by [templates]:
     * every value from the [collection] does the concatenating code emission inside each of [templates].
     *
     * @return An [Array] of all generated code blocks or `null` if the provided [templates] are `null` or empty.
     */
    internal fun <T> generateCodeBlocks(
        collection: Collection<T>?,
        vararg templates: (CodeEmitters<T>) -> String
    ): Array<String>? {

        // Fast exit if there is nothing to process.
        if (collection.isNullOrEmpty()) return null

        // Preparing all layouts to inflate the code and related code emitters.
        val templateCount   = templates.size
        val codeEmitters    = arrayOfNulls<StringBuildersCodeEmitters<T>>(templateCount)
        val templateLayouts = arrayOfNulls<String>(templateCount)
        for (index in templates.indices) {
            templateLayouts[index] = templates[index].invoke(
                StringBuildersCodeEmitters<T>().also { codeEmitters[index] = it }
            )
        }

        // Taking into account all items of the collection.
        for (item in collection) {
            codeEmitters.forEach { it?.append(item) }
        }

        // All null accessing should be safe as was initialized with non-null values above.
        return Array(templateCount) { index ->
            templateLayouts[index]?.format(*codeEmitters[index]?.getStringBuilders()!!)!!
        }

    }

    /**
     * Provides correct code representation for a nullable [string].
     */
    internal fun escape(string: String?): String = string?.let { "\"$it\"" } ?: "null"

}



// Private.

/**
 * An implementation of [CodeEmitters] storing and providing all code emissions with [StringBuilder]s.
 */
private class StringBuildersCodeEmitters<T> : CodeEmitters<T> {

    override fun emitCode(codeEmitter: (T) -> String): String {
        codeEmitters.add(codeEmitter)
        stringBuilders.add(StringBuilder())
        return "%${codeEmitters.size}\$s"
    }

    /**
     * Invokes all [codeEmitters] with an [item] concatenating their results.
     */
    fun append(item: T) {
        var index = 0
        val size  = stringBuilders.size
        while (index < size) {
            stringBuilders[index].append(codeEmitters[index].invoke(item))
            ++index
        }
    }

    fun getStringBuilders(): Array<StringBuilder> = stringBuilders.toTypedArray()

    private val codeEmitters   = mutableListOf<(T) -> String>()
    private val stringBuilders = mutableListOf<StringBuilder>()

}
