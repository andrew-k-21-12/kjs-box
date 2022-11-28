package io.github.andrewk2112.hooks

import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.localization.LocalizationEngine
import io.github.andrewk2112.utility.StringMapper
import org.kodein.di.direct
import org.kodein.di.instance
import react.useState

// Public.

/**
 * Invokes the hook to get the localizator for retrieving translated strings by keys.
 */
fun useLocalizator(): StringMapper = useLocalizationEngine().useLocalizator()

/**
 * Invokes the hook to get both the current active language and the localizator.
 */
fun useCurrentLanguageAndLocalizator(): Pair<String, StringMapper> =
    useLocalizationEngine().useCurrentLanguageAndLocalizator()



// Private.

/**
 * Retrieves an instance of the [LocalizationEngine] by the state hook.
 */
private fun useLocalizationEngine(): LocalizationEngine =
    useState { di.direct.instance<LocalizationEngine>() }.component1()
