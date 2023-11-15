package io.github.andrewk2112.kjsbox.frontend.core.localization

import io.github.andrewk2112.kjsbox.frontend.core.Environment
import io.github.andrewk2112.kjsbox.frontend.core.jsmodules.i18next.i18next
import io.github.andrewk2112.kjsbox.frontend.core.jsmodules.i18next.reactI18next
import io.github.andrewk2112.kjsbox.frontend.core.jsmodules.i18next.i18nextBrowserLanguageDetector
import io.github.andrewk2112.kjsbox.frontend.core.jsmodules.i18next.i18nextResourcesToBackend
import js.import.import
import kotlinext.js.js

/**
 * Helps to access localizations.
 */
class LocalizationEngine {

    // Initialization.

    init {
        i18next
            // Pointing to translations to be loaded only on demand.
            // Bundling into JS files shows better minification results
            // in comparison to JSONs being fetched by i18next-http-backend.
            .use(
                i18nextResourcesToBackend.resourcesToBackend { language, namespace ->
                    import<dynamic>("./locales/$language/$namespace.json")
                }
            )
            .use(i18nextBrowserLanguageDetector.LanguageDetector) // detecting browser's language automatically
            .use(reactI18next.initReactI18next)                   // using React integrations
            .init(createI18NextOptions())
    }



    // Public.

    /**
     * Returns a [Localizator] for retrieving translated strings by keys.
     */
    fun useLocalizator(): Localizator = reactI18next.useTranslation()::t

    /**
     * The same as [useLocalizator] but also downloads a [namespace] group of translations.
     */
    fun useLocalizator(namespace: String): Localizator = reactI18next.useTranslation(namespace)::t

    /**
     * The same as [useLocalizator] but also downloads multiple [namespaces] of translations.
     */
    fun useLocalizator(vararg namespaces: String): Localizator = reactI18next.useTranslation(*namespaces)::t

    /**
     * Returns both the current active language and the [Localizator].
     */
    fun useCurrentLanguageAndLocalizator(): Pair<String, Localizator> =
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
            debug         = Environment.buildMode == Environment.BuildMode.DEVELOPMENT
            fallbackLng   = fallbackLanguageCode       // a language to be used by default
            load          = "languageOnly"             // ignoring countries while determining a language
            defaultNS     = emptyArray<String>()       // <- these empty namespace options turn off pointless attempts
            ns            = emptyArray<String>()       // <- to fetch central namespace translation files by default
            interpolation = js { escapeValue = false } // React provides its own escaping
        }
    }

}
