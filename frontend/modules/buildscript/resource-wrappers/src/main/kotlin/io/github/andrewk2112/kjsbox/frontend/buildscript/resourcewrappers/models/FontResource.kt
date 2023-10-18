package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models

/**
 * Wraps all required metadata for each family of font resources.
 *
 * @param fontFamily   Font's basic name.
 * @param relativePath See [HavingRelativePath.relativePath].
 * @param variants     Keeps all metadata about each particular [Variant] of the font.
 */
internal class FontResource(
    internal val fontFamily: String,
    override val relativePath: String,
    internal val variants: List<Variant>
) : HavingRelativePath {

    /**
     * Describes each available variant of the font family.
     *
     * @param variantName          Variant's base name - usually "Regular", "Bold" and so on.
     * @param format               Font's format - usually comes as an extension: "woff2", "ttf" and so on.
     * @param relativeFontPath     Describes the full relative path to the particular font resource of the family
     *                             starting from the root resources directory.
     * @param fallbackFontFamilies Font families to be used if the font can not be fetched or not available.
     */
    internal class Variant(
        internal val variantName: String,
        internal val format: String,
        internal val relativeFontPath: String,
        internal vararg val fallbackFontFamilies: String,
    )

}
