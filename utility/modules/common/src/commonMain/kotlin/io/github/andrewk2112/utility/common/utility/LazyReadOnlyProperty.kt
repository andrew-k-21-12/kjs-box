package io.github.andrewk2112.utility.common.extensions

import kotlin.properties.PropertyDelegateProvider
import kotlin.reflect.KProperty

/**
 * One more way to construct a [PropertyDelegateProvider]
 * when the type of object which owns the delegated property is not important.
 */
inline fun <T> PropertyDelegateProvider(crossinline provider: (KProperty<*>) -> T): PropertyDelegateProvider<Any?, T> =
    PropertyDelegateProvider { _, b -> provider(b) }
