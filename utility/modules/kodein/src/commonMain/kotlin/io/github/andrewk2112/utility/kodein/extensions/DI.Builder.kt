package io.github.andrewk2112.utility.kodein.extensions

import io.github.andrewk2112.utility.common.utility.Provider
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindProvider

/**
 * Similar to the original [bindProvider]
 * but also creates an on-demand [Provider]-based factory which calls the [creator] on each [Provider.get] invocation:
 *
 * ```kotlin
 * class HavingInjectedDependencies(private val dependency: Provider<Dependency>) {
 *
 *     fun doWithDependency() {
 *         dependency.get().doSomething() // calls the dependency's factory
 *     }
 *
 * }
 * ```
 */
inline fun <reified T: Any> DI.Builder.bindProvider(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline creator: DirectDI.() -> T
) {
    bindProvider(tag, overrides, creator)
    bindProvider(tag, overrides) { Provider { creator() } }
}
