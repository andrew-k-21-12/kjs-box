package io.github.andrewk2112.kjsbox.frontend.example.localization

import io.github.andrewk2112.kjsbox.frontend.core.Environment
import io.github.andrewk2112.kjsbox.frontend.core.Environment.BuildMode.DEVELOPMENT
import io.github.andrewk2112.kjsbox.frontend.example.localization.Language.ENGLISH
import io.github.andrewk2112.kjsbox.frontend.localization.LanguageManager
import io.github.andrewk2112.kjsbox.frontend.localization.LocalizationsGetter
import io.github.andrewk2112.kjsbox.frontend.localization.LocalizationsLoader
import io.github.andrewk2112.kjsbox.frontend.localization.i18next.I18NextLocalizationEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

/**
 * Provides localization and language features by abstract APIs.
 *
 * This class can have an interface extracted and located in a separate Gradle module,
 * but for now there is no need in it, as there are no substitutions of implementations yet.
 *
 * An instance of this class is better to be organized as a singleton,
 * because [I18NextLocalizationEngine] is a singleton under the hood anyway.
 *
 * @param coroutineScope Some long-living [CoroutineScope] to do [StateFlow] mapping and other various `suspend` stuff.
 */
class LocalizationEngine(
    coroutineScope: CoroutineScope
) : LocalizationsLoader<String>, LocalizationsGetter<String, String>, LanguageManager<Language> {

    // API.

    /**
     * See the docs for [I18NextLocalizationEngine.loadLocalizations].
     */
    override fun loadLocalizations(localizationsGroup: String) = implementation.loadLocalizations(localizationsGroup)

    override fun getLocalization(key: String): String = implementation.getLocalization(key)

    override fun changeLanguage(language: Language) = implementation.changeLanguage(language.asIsoString())

    override val currentLanguage: StateFlow<Language> by lazy {
        implementation.currentLanguage
            .map { it.asLanguage() }
            .stateIn(coroutineScope, SharingStarted.Lazily, implementation.currentLanguage.value.asLanguage())
    }



    // Private.

    private fun String.asLanguage(): Language = Language.entries.find { it.asIsoString() == this } ?: fallbackLanguage

    /**
     * This language is used as a fallback one when no required localization is found for a key
     * or when the [currentLanguage] can not be recognized from a raw ISO language code.
     */
    private val fallbackLanguage: Language inline get() = ENGLISH

    private val implementation = I18NextLocalizationEngine.getInstance(
        fallbackLanguage.asIsoString(),
        Environment.buildMode == DEVELOPMENT
    )

}
