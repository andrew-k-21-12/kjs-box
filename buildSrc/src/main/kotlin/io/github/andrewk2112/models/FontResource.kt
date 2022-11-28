package io.github.andrewk2112.models

/**
 * Wraps all required metadata for each family of font resources.
 *
 * @param fontFamily   Font's basic name.
 * @param relativePath A short sub-path
 *                     starting (excluding) from the directory with all resources of the font type
 *                     and ending (including) at the particular folder holding all font variants of the family.
 * @param variants     Keeps all metadata about each particular [Variant] of the font.
 */
internal class FontResource(
    internal val fontFamily: String,
    internal val relativePath: String,
    internal val variants: List<Variant>
) {

    /**
     * Describes each available variant of the font family.
     *
     * @param variantName      Variant's base name - usually "Regular", "Bold" and so on.
     * @param format           Font's format - usually comes as an extension: "woff2", "ttf" and so on.
     * @param relativeFontPath Describes the full relative path to the particular font resource of the family
     *                         starting from the root resources directory.
     */
    internal class Variant(
        internal val variantName: String,
        internal val format: String,
        internal val relativeFontPath: String
    )

}
