package io.github.andrewk2112.utility.react.components

import react.ChildrenBuilder
import react.FC
import react.Props
import react.HasDisplayName

/**
 * Creates and stores an instance of [FC].
 *
 * The main purpose of this factory is to enable construction features for [FC]s:
 * for example, it allows to use constructor dependency injections
 * without accessing any particular dependency injection component inside the [FC]'s body.
 *
 * Avoid unnecessary allocations of such factories, don't create them on each rendering cycle.
 */
abstract class FunctionalComponentFactory<P : Props> {

    // Public.

    /** The [FC] itself - use it as any other [FC] in places where it's needed to be rendered with provided [Props]. */
    val component: FC<P> by lazy {
        val name = createComponentName()
        val body: ChildrenBuilder.(P) -> Unit = { render(it) }
        if (name != null) FC(name, body) else FC(body)
    }



    // To be overridden.

    /**
     * Describe the [component]'s contents here as in the case of any other [FC]'s declaration.
     */
    protected abstract fun ChildrenBuilder.render(props: P)

    /**
     * Sets a custom [HasDisplayName.displayName] for the [component]:
     * if it's `null` then the [component] will be named with a slightly revamped class name of the factory,
     * see [createComponentName].
     */
    protected open val componentName: String? = null



    // Private.

    /**
     * Prefers a [componentName] in the first order or uses a class name with trimmed default naming parts.
     */
    private fun createComponentName(): String? =
        componentName
            ?: this::class.simpleName?.removeSuffix("Factory")
                                     ?.removeSuffix("Component")
                                     ?.removeSuffix("Functional")

}
