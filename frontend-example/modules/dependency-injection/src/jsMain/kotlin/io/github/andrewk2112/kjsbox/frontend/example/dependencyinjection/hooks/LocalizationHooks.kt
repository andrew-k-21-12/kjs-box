package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.hooks

import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationEngine
import io.github.andrewk2112.kjsbox.frontend.core.localization.Localizator

/**
 * Returns a [Localizator] for retrieving translated strings by keys.
 */
fun useLocalizator(): Localizator = useInjected<LocalizationEngine>().useLocalizator()

/**
 * The same as [useLocalizator] but also downloads a [namespace] group of translations.
 */
fun useLocalizator(namespace: String): Localizator = useInjected<LocalizationEngine>().useLocalizator(namespace)

/**
 * The same as [useLocalizator] but also downloads multiple [namespaces] of translations.
 */
fun useLocalizator(vararg namespaces: String): Localizator =
    useInjected<LocalizationEngine>().useLocalizator(*namespaces)

/**
 * Returns both the current active language and the [Localizator].
 */
fun useCurrentLanguageAndLocalizator(): Pair<String, Localizator> =
    useInjected<LocalizationEngine>().useCurrentLanguageAndLocalizator()
