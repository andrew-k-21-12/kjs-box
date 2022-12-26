package io.github.andrewk2112.hooks

import io.github.andrewk2112.dinjection.di
import org.kodein.di.direct
import org.kodein.di.instance
import react.useState

/**
 * Injects and returns an instance of the type [T] once for the entire life cycle of a component.
 */
inline fun <reified T : Any> useInjected(tag: Any? = null): T = useState { di.direct.instance<T>(tag) }.component1()
