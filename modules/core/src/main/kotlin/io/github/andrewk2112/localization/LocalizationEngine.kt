package io.github.andrewk2112.localization

import io.github.andrewk2112.Environment
import io.github.andrewk2112.jsmodules.i18next.i18next
import io.github.andrewk2112.jsmodules.i18next.i18nextBrowserLanguageDetector
import io.github.andrewk2112.jsmodules.i18next.i18nextHttpBackend
import io.github.andrewk2112.jsmodules.i18next.reactI18next
import kotlinext.js.js

/**
 * Helps to access localizations.
 * */
class LocalizationEngine {

    // Initialization.

    init {
        i18next
            .use(i18nextHttpBackend)                              // loading translations only on demand
            .use(i18nextBrowserLanguageDetector.LanguageDetector) // detecting browser's language automatically
            .use(reactI18next.initReactI18next)                   // using React integrations
            .init(createI18NextOptions())
    }



    // Public.

    /**
     * A hook to start getting localized strings.
     *
     * @return A localized strings getter.
     * */
    fun useLocalizator(): (String) -> String = reactI18next.useTranslation()::t



    // Private.

    /**
     * Creates all required options for the underlying translation library.
     *
     * @return Options in the format of the plain JS object.
     * */
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
