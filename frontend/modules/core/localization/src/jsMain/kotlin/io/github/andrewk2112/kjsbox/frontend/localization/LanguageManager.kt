package io.github.andrewk2112.kjsbox.frontend.localization

import kotlinx.coroutines.flow.StateFlow

/**
 * Allows to read current language and request to change it.
 */
interface LanguageManager<T> {

    /**
     * Changes current language setting.
     */
    fun changeLanguage(language: T)

    /** Retrieves current language, allows to observe it. */
    val currentLanguage: StateFlow<T>

}
