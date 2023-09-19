package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models

/**
 * Wraps all required metadata for each localization resource.
 *
 * @param name             Resource's name.
 * @param relativePath     See [HavingRelativePath.relativePath].
 * @param fullKey          Full translation key including all intermediate keys.
 * @param supportedLocales All locales having a translation for the composite key.
 */
internal class LocalizationResource(
    internal val name: String,
    override val relativePath: String,
    internal val fullKey: String,
    internal val supportedLocales: Set<String>
) : HavingRelativePath
