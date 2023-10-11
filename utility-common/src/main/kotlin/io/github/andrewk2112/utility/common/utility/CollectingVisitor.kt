package io.github.andrewk2112.utility.common.utility

/**
 * A generic collecting visitor: applied to [visit] a set of objects [T]
 * to collect the related important data [R] with [add] or [addOrReplace]
 * and [consume] the overall collected data eventually.
 */
abstract class CollectingVisitor<T, R, E : Exception> {

    // API.

    /**
     * Visits a particular [element] to possibly [add] or [addOrReplace] data related to it.
     *
     * @return An empty [Result] of the visiting: check it for possible exceptions.
     */
    abstract fun visit(element: T): Result<Unit, E>

    /**
     * Retrieves the overall collected data and resets the internal state.
     */
    fun consume(): List<R> {
        val result = collected.toList() // making a shallow copy
        collected.clear()
        return result
    }



    // Protected - to be used in inheritors.

    /**
     * Adds the processed data to be [consume]d.
     */
    protected fun R.add() {
        collected.add(this)
    }

    /**
     * Replaces a data element has been collected already.
     *
     * @param predicate   A predicate to find a particular data element.
     * @param replacement How to perform the replacement: the argument will be null
     *                    if nothing was found according to the [predicate].
     */
    protected fun addOrReplace(predicate: (R) -> Boolean, replacement: (R?) -> R) {
        val index = collected.indexOfFirst(predicate)
        if (index < 0) {
            collected.add(replacement.invoke(null))
        } else {
            collected[index] = replacement.invoke(collected[index])
        }
    }



    // Private.

    /** Stores the overall collected data. */
    private val collected = mutableListOf<R>()

}
