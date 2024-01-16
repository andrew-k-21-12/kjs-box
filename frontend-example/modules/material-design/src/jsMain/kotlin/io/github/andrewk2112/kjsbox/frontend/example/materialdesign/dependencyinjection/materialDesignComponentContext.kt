package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection

import react.*



// Public.

/**
 * Syntax sugar to provide or substitute a [MaterialDesignComponent] for all nested UI components.
 */
inline fun ChildrenBuilder.provideMaterialDesignComponent(
    component: MaterialDesignComponent,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    materialDesignComponentContext.Provider {
        value = component
        children()
    }

/**
 * Gets a scoped instance of [MaterialDesignComponent].
 */
fun useMaterialDesignComponent(): MaterialDesignComponent = useRequiredContext(materialDesignComponentContext)



// Private.

/** Sets and gets scoped [MaterialDesignComponent] instances. */
@PublishedApi
internal val materialDesignComponentContext: RequiredContext<MaterialDesignComponent> = createRequiredContext()
