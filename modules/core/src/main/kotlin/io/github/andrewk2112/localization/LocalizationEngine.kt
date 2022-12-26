package io.github.andrewk2112.localization

import io.github.andrewk2112.Environment
import io.github.andrewk2112.jsmodules.i18next.i18next
import io.github.andrewk2112.jsmodules.i18next.i18nextBrowserLanguageDetector
import io.github.andrewk2112.jsmodules.i18next.i18nextHttpBackend
import io.github.andrewk2112.jsmodules.i18next.reactI18next
import kotlinext.js.js

/**
 * Helps to access localizations.
 */
internal class LocalizationEngine {

    // Initialization.

    init {
        i18next
            .use(i18nextHttpBackend)             // loading translations only on demand
            .use(i18nextBrowserLanguageDetector) // detecting browser's language automatically
            .use(reactI18next.initReactI18next)  // using React integrations
            .init(createI18NextOptions())
    }



    // Internal.

    /**
     * Returns a [Localizator] for retrieving translated strings by keys.
     */
    internal fun useLocalizator(): Localizator = reactI18next.useTranslation()::t

    /**
     * Returns both the current active language and the [Localizator].
     */
    internal fun useCurrentLanguageAndLocalizator(): Pair<String, Localizator> =
        with(reactI18next.useTranslation()) { Pair(i18n.language, ::t) }



    // Private.

    /**
     * Creates all required options for the underlying translation library.
     *
     * @return Options in the format of the plain JS object.
     */
    private fun createI18NextOptions(): dynamic {
        val fallbackLanguageCode = Language.ENGLISH.code // it's not possible to inject a string from Kotlin otherwise
        return js {
            debug = Environment.buildMode == Environment.BuildMode.DEVELOPMENT
            fallbackLng = fallbackLanguageCode // a language to be used by default
            load = "languageOnly"              // ignoring countries while determining a language
            interpolation = js {
                escapeValue = false            // React provides its own escaping
            }
        }
    }

}
