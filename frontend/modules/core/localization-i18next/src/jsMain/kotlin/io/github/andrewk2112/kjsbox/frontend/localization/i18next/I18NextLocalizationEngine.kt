package io.github.andrewk2112.kjsbox.frontend.localization.i18next

import io.github.andrewk2112.kjsbox.frontend.localization.LanguageManager
import io.github.andrewk2112.kjsbox.frontend.localization.LocalizationsGetter
import io.github.andrewk2112.kjsbox.frontend.localization.LocalizationsLoader
import io.github.andrewk2112.kjsbox.frontend.localization.i18next.jsmodules.i18next
import io.github.andrewk2112.kjsbox.frontend.localization.i18next.jsmodules.i18nextBrowserLanguageDetector
import io.github.andrewk2112.kjsbox.frontend.localization.i18next.jsmodules.i18nextResourcesToBackend
import io.github.andrewk2112.kjsbox.frontend.localization.i18next.jsmodules.reactI18next
import js.import.import
import kotlinext.js.js
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Provides localization and language features backed by **i18next**.
 *
 * It is organized as a singleton because **i18next** is a singleton under the hood anyway.
 */
class I18NextLocalizationEngine private constructor(
    fallbackLanguage: String,
    isDebug: Boolean,
) : LocalizationsLoader<String>, LocalizationsGetter<String, String>, LanguageManager<String> {

    // API.

    companion object {

        /**
         * Retrieves a single instance if this class.
         *
         * **Not thread-safe!**
         *
         * @param fallbackLanguage Language to be used when no localization is found for a requested language.
         * @param isDebug          Enables additional debugging things such as extended logging.
         */
        fun getInstance(fallbackLanguage: String, isDebug: Boolean): I18NextLocalizationEngine =
            instance ?: I18NextLocalizationEngine(fallbackLanguage, isDebug).also { instance = it }

        private var instance: I18NextLocalizationEngine? = null

    }

    /**
     * **Attention** - this method uses a React hook:
     * make sure to respect all rules related to hooks when invoking it.
     *
     * The method call should work out (return) immediately:
     * it uses React Suspense to indicate loading state while fetching localizations asynchronously.
     */
    override fun loadLocalizations(localizationsGroup: String) {
        reactI18next.useTranslation(localizationsGroup)
    }

    override fun getLocalization(key: String): String = i18next.t(key)

    /**
     * This method should return immediately,
     * but it can take some time to apply the requested [language]
     * and reflect the corresponding update in the [currentLanguage].
     */
    override fun changeLanguage(language: String) = i18next.changeLanguage(language)

    override val currentLanguage: StateFlow<String> by ::_currentLanguage



    // Initialization.

    /**
     * Creates all required options for the underlying localization library.
     *
     * @return Options supported by **i18next** in the format of plain JS object.
     */
    private fun createI18NextOptions(fallbackLanguage: String, isDebug: Boolean): dynamic =
        js {
            debug         = isDebug
            fallbackLng   = fallbackLanguage           // a language to be used by default
            load          = "languageOnly"             // ignoring countries while determining a language
            defaultNS     = emptyArray<String>()       // <- these empty namespace options turn off pointless attempts
            ns            = emptyArray<String>()       // <- to fetch central namespace translation files by default
            interpolation = js { escapeValue = false } // React provides its own escaping
        }

    /** An actual holder and provider of the current language value. */
    private val _currentLanguage = MutableStateFlow(fallbackLanguage)

    init {
        i18next
            // Pointing to translations to be loaded only on demand.
            // Bundling translations into JS files shows better minification results
            // in comparison to JSONs being fetched by i18next-http-backend.
            .use(
                i18nextResourcesToBackend.resourcesToBackend { language, namespace ->
                    import<dynamic>("./locales/$language/$namespace.json")
                }
            )
            .use(i18nextBrowserLanguageDetector.LanguageDetector) // detecting browser's language automatically
            .use(reactI18next.initReactI18next)                   // using React integrations
            .init(createI18NextOptions(fallbackLanguage, isDebug))
        // There is no need to turn off this observation,
        // as there is only one instance of the localization engine used everywhere.
        i18next.on<String>("languageChanged") {
            _currentLanguage.value = it
        }
    }

}
