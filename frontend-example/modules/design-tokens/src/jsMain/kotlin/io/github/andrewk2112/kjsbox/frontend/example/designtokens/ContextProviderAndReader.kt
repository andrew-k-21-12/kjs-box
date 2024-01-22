package io.github.andrewk2112.kjsbox.frontend.example.designtokens

import io.github.andrewk2112.kjsbox.frontend.core.hooks.useWindowInnerWidthMonitor
import react.*



// Public.

/**
 * Setups the [Context] for all further React components
 * by registering all required changes of the environment to update the provided [Context] accordingly.
 */
val designTokensContextProvider = FC<PropsWithChildren> { props ->
    val currentContext    = useRef<Context>()
    val currentScreenSize = Context.ScreenSize.fromRawWidth(useWindowInnerWidthMonitor())
    if (currentContext.current?.screenSize != currentScreenSize) {
        currentContext.current = Context(currentScreenSize, Context.ColorMode.LIGHT)
    }
    contextRequiredContext.Provider(currentContext.current!!) { // the value is guaranteed by the condition above
        +props.children
    }
}

/**
 * Reads the dynamically updated [Context].
 */
fun useDesignTokensContext(): Context = useRequiredContext(contextRequiredContext)



// Private.

/** Sets and gets scoped [Context] instances. */
private val contextRequiredContext: RequiredContext<Context> = createRequiredContext()
