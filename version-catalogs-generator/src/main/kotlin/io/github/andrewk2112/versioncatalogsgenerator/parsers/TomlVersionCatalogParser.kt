package io.github.andrewk2112.versioncatalogsgenerator.parsers

import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog
import org.tomlj.Toml
import org.tomlj.TomlTable
import kotlin.io.path.Path

/**
 * Parses TOML version catalogs.
 */
internal class TomlVersionCatalogParser {

    // Interface.

    /**
     * Parses a version catalog located by a [path].
     *
     * @return The [ParsedVersionCatalog] or `null` if the catalog has no meaningful data.
     *
     * @throws Exception In the case of any error or catalog's format mismatch.
     */
    @Throws(Exception::class)
    internal fun parseCatalog(path: String): ParsedVersionCatalog? {

        // Trying to read and parse a file by the path.
        val parseResult = Toml.parse(Path(path))
                              .apply {
                                  if (hasErrors()) {
                                      throw Exception("Could not parse a TOML catalog file: ${errors().joinToString()}")
                                  }
                              }

        // Parsing all expected values.
        val versions  = parseResult.getTable("versions")?.parseVersions()
        val libraries = parseResult.getTable("libraries")?.parseEachEntry { toLibrary(versions) }
        val plugins   = parseResult.getTable("plugins")?.parseEachEntry   { toPlugin(versions) }

        // Checking if there is some meaningful data parsed in the end.
        if (versions == null && libraries == null && plugins == null) return null

        return ParsedVersionCatalog(versions, libraries, plugins)

    }



    // Private.

    /**
     * Can return an empty result if there are no versions present,
     * but if there are some version keys, all of them must be provided correctly.
     */
    @Suppress("UnnecessaryOptInAnnotation") // old Kotlin versions in build scripts require the opt-in below
    @OptIn(ExperimentalStdlibApi::class)
    @Throws(Exception::class)
    private fun TomlTable.parseVersions(): Map<String, String>? =
        if (isEmpty) null else buildMap {
            for (key in keySet()) {
                put(key, getString(key) ?: throw Exception("Nullable versions are not supported"))
            }
        }

    /**
     * Can return an empty result if the [TomlTable] is empty,
     * but expects all its entries to be parsed into non-`null` values correctly otherwise
     * (throws an [Exception] if there are some `null` or impossible to parse values).
     *
     * @param parse This lambda can and actually is expected to throw [Exception]s.
     */
    @Suppress("UnnecessaryOptInAnnotation") // old Kotlin versions in build scripts require the opt-in below
    @OptIn(ExperimentalStdlibApi::class)
    @Throws(Exception::class)
    private fun <T> TomlTable.parseEachEntry(parse: TomlTable?.() -> T): Map<String, T>? =
        if (isEmpty) null else buildMap {
            for (key in keySet()) {
                put(key, getTable(key).parse())
            }
        }

    @Throws(Exception::class)
    private fun TomlTable?.toLibrary(availableVersions: Map<String, String>?): ParsedVersionCatalog.Library {
        if (this == null) throw Exception("Nullable libraries are not supported")
        val module  = getString("module")
        val group   = getString("group")
        val name    = getString("name")
        val version = extractVersion(availableVersions)
        return if (module != null) {
            val (groupFromModule, nameFromModule) = module.split(":")
            ParsedVersionCatalog.Library(groupFromModule, nameFromModule, version)
        } else if (group != null && name != null) {
            ParsedVersionCatalog.Library(group, name, version)
        } else {
            throw Exception("Malformed library description has been encountered")
        }
    }

    @Throws(Exception::class)
    private fun TomlTable?.toPlugin(availableVersions: Map<String, String>?): ParsedVersionCatalog.Plugin {
        if (this == null) throw Exception("Nullable plugins are not supported")
        return ParsedVersionCatalog.Plugin(
            getString("id") ?: throw Exception("Each plugin description must have an \"id\" present"),
            extractVersion(availableVersions)
        )
    }

    /**
     * Nullable (not provided) versions are supported,
     * but if there is some version reference present, it must be satisfied in the corresponding **versions** block.
     */
    @Throws(Exception::class)
    private fun TomlTable.extractVersion(availableVersions: Map<String, String>?): String? =
        getString("version.ref")?.let {
            availableVersions?.get(it) ?: throw Exception("Unknown version reference")
        }

}
