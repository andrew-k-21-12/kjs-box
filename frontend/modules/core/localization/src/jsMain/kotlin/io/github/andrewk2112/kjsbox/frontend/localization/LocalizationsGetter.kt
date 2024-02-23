package io.github.andrewk2112.kjsbox.frontend.localization

/**
 * Provides means to retrieve localizations [V] by keys [K].
 */
interface LocalizationsGetter<K, V> {

    /**
     * Retrieves a localization for a [key].
     */
    fun getLocalization(key: K): V

}
