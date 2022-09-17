package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useRefScrollMonitor
import kotlinx.css.*
import org.w3c.dom.Element
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useRef

// TODO:
//  0. Suggest styles wrapping to avoid direct usage of kotlin-styled-next (another proposal and PR)!
//     Use the latest version of the kotlin-styled-next with my PR (drop my stylesheets package).
//  1. Optimize dynamic CSS holders somehow: now they are storing common styles in a duplicating manner.

// TODO:
//  2. Dependencies on inner variables are not good (in components).
//     Also, it can be reasonable to avoid lots of singletons (e.g., for stateless views) which always live in the memory.
//     Also, it can be reasonable to wrap functional components into classes and separate from their states.
//  3. Simplify WindowWidthMonitor, icons.kt.
//  4. Try to get rid of injectGlobal(...) everywhere as it adds style tags into the head.
//  5. Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere!

// TODO:
//  6. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes.
//  7. Fix webpack warnings and do a clean-up for its scripts.
//  8. Write some custom server with all required configs (caches, routing) and place it here.
//  9. Introduce better modular structure (which should separate resources and style values as well).
//  10. Write about the project's features in the central README.md.
//  11. Change the package name when the project gets its final name.
//  12. Clean-up Gradle scripts.
//  13. Remove locale keys unmet in the source code when bundling.
//  14. Set internal visibility modifiers for entities internal to modules.



// Public.

external interface ContentProps : Props {
    var topSpacing: Double
    var onScrolled: (scrollTop: Double, deltaY: Double) -> Unit
}

val content = FC<ContentProps> { props ->

    // State.

    val context    = useAppContext()
    val contentRef = useRef<Element>(null)

    useRefScrollMonitor(contentRef, onScrolled = props.onScrolled)



    // Rendering.

    withClassName(div, ContentStyles.container(context).name) {

        ref = contentRef

        // Top spacing to fit the header.
        withClassName(div, ContentStyles.headerSpacer(props.topSpacing).name) {}

        // Design descriptions block.
        contentDesign {}

    }

}



// Private.

private object ContentStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        flexGrow = 1.0
        overflow = Overflow.scroll
        backgroundColor = Theme.palette.surface2(it)
    }

    val headerSpacer: DynamicCssProvider<Double> by dynamicCss {
        height = it.px
    }

}
