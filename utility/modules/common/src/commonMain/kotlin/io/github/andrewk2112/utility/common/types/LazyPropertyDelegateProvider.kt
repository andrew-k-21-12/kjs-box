package io.github.andrewk2112.utility.common.types

import kotlin.properties.PropertyDelegateProvider

typealias LazyPropertyDelegateProvider<T> = PropertyDelegateProvider<Any?, Lazy<T>>
