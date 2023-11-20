package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks

import io.github.andrewk2112.kjsbox.frontend.core.hooks.useInjected
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationEngine
import io.github.andrewk2112.kjsbox.frontend.core.localization.Localizator
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.rootComponent



// Public.

/**
 * Returns a [Localizator] for retrieving translated strings by keys.
 */
fun useLocalizator(): Localizator = useLocalizationEngine().useLocalizator()

/**
 * The same as [useLocalizator] but also downloads a [namespace] group of translations.
 */
fun useLocalizator(namespace: String): Localizator = useLocalizationEngine().useLocalizator(namespace)

/**
 * The same as [useLocalizator] but also downloads multiple [namespaces] of translations.
 */
fun useLocalizator(vararg namespaces: String): Localizator = useLocalizationEngine().useLocalizator(*namespaces)

/**
 * Returns both the current active language and the [Localizator].
 */
fun useCurrentLanguageAndLocalizator(): Pair<String, Localizator> =
    useLocalizationEngine().useCurrentLanguageAndLocalizator()



// Private.

private fun useLocalizationEngine(): LocalizationEngine = rootComponent.useInjected<LocalizationEngine>()
