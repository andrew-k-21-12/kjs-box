package io.github.andrewk2112.kjsbox.frontend.example.localization

import io.github.andrewk2112.kjsbox.frontend.example.localization.Language.ENGLISH
import io.github.andrewk2112.kjsbox.frontend.example.localization.Language.RUSSIAN

/**
 * All supported languages.
 */
enum class Language { RUSSIAN, ENGLISH }

fun Language.asIsoString(): String =
    when (this) {
        RUSSIAN -> "ru"
        ENGLISH -> "en"
    }
