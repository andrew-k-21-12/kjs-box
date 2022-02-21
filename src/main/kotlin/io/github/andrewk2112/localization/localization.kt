package io.github.andrewk2112.localization

import io.github.andrewk2112.dinjection.di
import org.kodein.di.instance

/** A localization features accessor. */
val localization: LocalizationEngine by di.instance()
