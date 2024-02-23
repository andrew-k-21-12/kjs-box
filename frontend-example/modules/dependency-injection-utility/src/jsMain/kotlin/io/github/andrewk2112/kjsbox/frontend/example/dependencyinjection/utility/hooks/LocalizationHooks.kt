package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent
import io.github.andrewk2112.kjsbox.frontend.example.localization.Language
import io.github.andrewk2112.kjsbox.frontend.example.localization.LocalizationEngine
import io.github.andrewk2112.utility.coroutines.react.extensions.asReactState



// Public.

/**
 * Syntax sugar to differentiate between regular [String]s and those representing keys to get translations for.
 */
typealias LocalizationKey = String

/**
 * Retrieves a localized [String] corresponding to a provided [LocalizationKey].
 */
typealias Localizator = (LocalizationKey) -> String

/**
 * Returns a [Localizator] for retrieving localized strings by [LocalizationKey]s.
 */
fun useLocalizator(): Localizator =
    useLocalizationEngine().run {
        currentLanguage.asReactState()
        ::getLocalization
    }

/**
 * The same as [useLocalizator] but also downloads a [namespace] group of translations.
 */
fun useLocalizator(namespace: String): Localizator =
    useLocalizationEngine().run {
        loadLocalizations(namespace)
        ::getLocalization
    }

/**
 * Returns both the current active language and a [Localizator].
 */
fun useCurrentLanguageAndLocalizator(): Pair<Language, Localizator> =
    useLocalizationEngine().run {
        Pair(currentLanguage.asReactState().component1(), ::getLocalization)
    }



// Private.

private fun useLocalizationEngine(): LocalizationEngine = useRootComponent().getLocalizationEngine()
