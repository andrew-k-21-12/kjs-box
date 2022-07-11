package io.github.andrewk2112.resources.endpoints

/**
 * Wraps the endpoint to the root Material Design page.
 *
 * Helps to separate concerns, reuse endpoint values from a single place and reduce string allocations.
 *
 * Other alternatives:
 * - resources provided by webpack - bad, as webpack doesn't generate Kotlin type-safe classes
 * - wrapping values into functions - will not work, as new types are needed anyway for classes with multiple properties
 * - Kotlin objects - need allocations, as translated into JS functions, not inlined (no benefit comparing to classes)
 *
 * If this or similar classes start to live forever or used in multiple places at the same time,
 * it can be better to use objects instead or some DI-based mechanism.
 * */
class MaterialDesignEndpoint {

    /** The endpoint value pointing to the Material Design page. */
    val value = "https://material.io/design"

}
