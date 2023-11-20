package io.github.andrewk2112.kjsbox.frontend.core.hooks

import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance
import react.useState

/**
 * Injects and returns an instance of type [T] from a provided [DI] source,
 * the injection happens once for the entire life cycle of component.
 */
inline fun <reified T : Any> DI.useInjected(tag: Any? = null): T = useState { direct.instance<T>(tag) }.component1()
