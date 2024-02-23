package io.github.andrewk2112.kjsbox.frontend.localization

/**
 * Loads and initializes groups ([T]) of localizations.
 */
interface LocalizationsLoader<T> {

    /**
     * Loads a particular [localizationsGroup].
     */
    fun loadLocalizations(localizationsGroup: T)

}
