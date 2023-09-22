package io.github.andrewk2112.kjsbox.frontend.core.localization

/**
 * Syntax sugar to differentiate between regular [String]s and those representing raw keys to get translations for.
 */
typealias LocalizationKey = String

/**
 * Retrieves a translation [String] corresponding to the provided [LocalizationKey].
 */
typealias Localizator = (LocalizationKey) -> String
