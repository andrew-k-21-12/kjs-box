package io.github.andrewk2112.hooks

import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.localization.LocalizationEngine
import org.kodein.di.direct
import org.kodein.di.instance

/**
 * Invokes the hook to get the localizator for retrieving translated strings by keys.
 * */
fun useLocalizator(): (String) -> String =
    useStateGetterOnce { di.direct.instance<LocalizationEngine>() }.useLocalizator()
