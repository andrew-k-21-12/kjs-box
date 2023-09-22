package io.github.andrewk2112.versioncatalogsgenerator.models

/**
 * All supported version catalog data.
 */
internal class ParsedVersionCatalog(
    internal val versions:  Map<String, String>?,
    internal val libraries: Map<String, Library>?,
    internal val bundles:   Map<String, List<String>>?,
    internal val plugins:   Map<String, Plugin>?
) {

    /**
     * Parsed version catalog library data.
     */
    internal class Library(
        internal val group:   String,
        internal val name:    String,
        internal val version: String?
    )

    /**
     * Parsed version catalog plugin data.
     */
    internal class Plugin(
        internal val id:      String,
        internal val version: String?
    )

}
