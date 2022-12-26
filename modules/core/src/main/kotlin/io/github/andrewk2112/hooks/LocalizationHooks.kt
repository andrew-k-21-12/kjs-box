package io.github.andrewk2112.hooks

import io.github.andrewk2112.localization.LocalizationEngine
import io.github.andrewk2112.localization.Localizator

/**
 * Returns a [Localizator] for retrieving translated strings by keys.
 */
fun useLocalizator(): Localizator = useInjected<LocalizationEngine>().useLocalizator()

/**
 * Returns both the current active language and the [Localizator].
 */
fun useCurrentLanguageAndLocalizator(): Pair<String, Localizator> =
    useInjected<LocalizationEngine>().useCurrentLanguageAndLocalizator()
