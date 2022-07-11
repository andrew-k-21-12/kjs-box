package io.github.andrewk2112.ui.components.md.content

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useRefScrollMonitor
import kotlinx.css.*
import org.w3c.dom.Element
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.span
import react.useRef

// TODO:
//  0. Suggest styles wrapping to avoid direct usage of kotlin-styled-next (another proposal and PR)!
//     Use the latest version of the kotlin-styled-next with my PR (drop my stylesheets package and AspectRatio extension).
//  1. Optimize dynamic CSS holders somehow: now they are storing common styles in a duplicating manner.

// TODO:
//  2. Dependencies on inner variables are not good (in components).
//     Also, it can be reasonable to avoid lots of singletons (e.g., for stateless views) which always live in the memory.
//     Also, it can be reasonable to wrap functional components into classes and separate from their states.
//  3. Simplify WindowWidthMonitor, icons.kt.
//  4. Include fonts via webpack instead.
//  5. Try to get rid of injectGlobal(...) everywhere as it adds style tags into the head.
//  6. Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere!

// TODO:
//  7. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes.
//  8. Fix webpack warnings and do a clean-up for its scripts.
//  9. Write some custom server with all required configs (caches, routing) and place it here.
//  10. Extract a module with a framework itself.



// Public.

class ContentProps private constructor(var onScrolled: (scrollTop: Double, deltaY: Double) -> Unit) : Props

val content = FC<ContentProps> { props ->

    // State.

    val context    = useAppContext()
    val contentRef = useRef<Element>(null)

    useRefScrollMonitor(contentRef, onScrolled = props.onScrolled)



    // Rendering.

    withClassName(ReactHTML.div, ContentStyles.container(context).name) {

        ref = contentRef



        // FIXME: To be replaced with actual contents.

        withClassName(span, ContentStyles.tempText.name) {
            +"""
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut 
                labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco 
                laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in 
                voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat 
                non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            """.trimIndent()
        }



    }

}



// Private.

private object ContentStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        position = Position.absolute
        left     = StyleValues.sizes.absolute280
        top      = 0.px
        bottom   = 0.px
        right    = 0.px
        overflow = Overflow.scroll
        backgroundColor = Theme.palette.surface2(it)
    }

    val tempText: NamedRuleSet by css {
        fontSize = 5.rem
    }

}
