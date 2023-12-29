package io.github.andrewk2112.kjsbox.frontend.core.hooks

import react.useEffect
import react.useMemo
import kotlin.reflect.KClass



// Public.

/**
 * The key difference with regular [useMemo]
 * is that this hook doesn't construct a new instance with the provided [callback]
 * while an instance of the same type [T] and [dependencies] list is already in use:
 * so this already-in-use instance gets returned instead.
 */
inline fun <reified T> useMemoWithReferenceCount(vararg dependencies: Any?, crossinline callback: () -> T): T {

    // Each time dependencies change or when the component becomes demounted,
    // the corresponding reference count gets decreased or removed at all.
    useEffect(dependencies) {
        cleanup {
            val key = UsageKey(T::class, dependencies)
            referencedValues[key]?.let { value ->
                if (value.referenceCount <= 1) {
                    referencedValues.remove(key)
                } else {
                    --value.referenceCount
                }
            }
        }
    }

    // Constructing a new instance or reusing an existing one according to the dependencies,
    // increasing the reference count on each attempt to use the same instance with the same context (dependencies).
    return useMemo(dependencies) {
        referencedValues.getOrPut(UsageKey(T::class, dependencies)) {
            ValueWithReferenceCount(callback(), 0)
        }.also {
            ++it.referenceCount
        }.value as T
    }

}



// Internal.

/**
 * Marks and distinguishes each unique usage
 * based on which [clazz] is going to be constructed and with which [dependencies].
 */
@PublishedApi
internal class UsageKey(private val clazz: KClass<*>, private val dependencies: Array<out Any?>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false
        return other is UsageKey && clazz == other.clazz && dependencies.contentEquals(other.dependencies)
    }

    override fun hashCode(): Int = 31 * clazz.hashCode() + dependencies.contentHashCode()

}

/**
 * Holds some typed [value] with its [referenceCount].
 */
@PublishedApi
internal class ValueWithReferenceCount<T>(val value: T, var referenceCount: Long)

/** Keeps all referenced values with their contexts. */
@PublishedApi
internal val referencedValues = mutableMapOf<UsageKey, ValueWithReferenceCount<*>>()
