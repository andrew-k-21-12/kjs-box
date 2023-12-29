package io.github.andrewk2112.utility.kodein

import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.instance

/**
 * A compilation of convenience means to construct an instance of Kodein component
 * and provide (inject) its dependencies directly.
 */
value class KodeinDirectInjection private constructor(@PublishedApi internal val kodeinDirect: DirectDI) {

    /**
     * Accepts all [modules] to provide (inject) dependencies directly from.
     */
    constructor(vararg modules: DI.Module) : this(
        DI {
            modules.forEach(::import)
        }.direct
    )

    /**
     * Directly (in a non-lazy manner) provides an instance of type [T].
     */
    inline operator fun <reified T> invoke(): T = kodeinDirect.instance()

}
