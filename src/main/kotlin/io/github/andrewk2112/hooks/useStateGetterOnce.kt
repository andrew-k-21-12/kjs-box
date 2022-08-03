package io.github.andrewk2112.hooks

import react.useState

/**
 * Creates some value only once (regardless from rendering cycles) inside a functional component.
 *
 * @param initializer A value factory.
 *
 * @return A created function component lifecycle-bound value.
 * */
fun <R> useStateGetterOnce(initializer: () -> R): R = useState(initializer).component1()
