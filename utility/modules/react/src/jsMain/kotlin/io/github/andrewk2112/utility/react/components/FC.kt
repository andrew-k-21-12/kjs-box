@file:Suppress("FunctionName")

package io.github.andrewk2112.utility.react.components

import io.github.andrewk2112.utility.common.extensions.PropertyDelegateProvider
import io.github.andrewk2112.utility.common.types.LazyPropertyDelegateProvider
import react.ChildrenBuilder
import react.FC
import react.Props
import react.ReactDsl
import kotlin.lazy

/**
 * Prepares a React component named with its declaration variable name:
 *
 * ```kotlin
 * val MyComponent by FC {} // will be named as "MyComponent" in React Developer Tools
 * ```
 */
fun FC(block: @ReactDsl ChildrenBuilder.() -> Unit): LazyPropertyDelegateProvider<FC<Props>> =
    PropertyDelegateProvider {
        lazy { FC(it.name, block) }
    }

/**
 * The same as [io.github.andrewk2112.utility.react.components.FC] but having typed [Props].
 */
fun <P : Props> FC(block: @ReactDsl ChildrenBuilder.(props: P) -> Unit): LazyPropertyDelegateProvider<FC<P>> =
    PropertyDelegateProvider {
        lazy { FC(it.name, block) }
    }
