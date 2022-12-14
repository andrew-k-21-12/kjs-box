package io.github.andrewk2112.md.components

import dom.Element
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Context.ScreenSize.DESKTOP
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.usePrevious
import io.github.andrewk2112.hooks.useRefHeightMonitor
import io.github.andrewk2112.md.components.content.*
import io.github.andrewk2112.md.components.header.header
import io.github.andrewk2112.md.components.menu.menu
import io.github.andrewk2112.md.styles.AnimationStyles
import io.github.andrewk2112.md.styles.FontStyles
import io.github.andrewk2112.md.styles.ShadowStyles
import io.github.andrewk2112.md.styles.TransitionStyles
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.HasCssSuffix
import kotlinx.css.*
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.Overflow
import kotlinx.css.Position
import kotlinx.css.pct
import kotlinx.css.properties.*
import react.*
import react.dom.events.MouseEventHandler
import react.dom.events.UIEventHandler
import react.dom.html.ReactHTML.aside
import react.dom.html.ReactHTML.div

// TODO - optimizations and modularization:
//  1. Suggest styles (classes) wrapping to avoid the direct usage of kotlin-styled-next (another proposal and PR).
//  2. Use the latest version of the kotlin-styled-next with my PR (drop my stylesheets package).
//  3. Optimize dynamic CSS holders somehow: now they are storing common styles in a duplicating manner,
//     maybe it is even better to drop this idea and use a combination of the React context and media queries?
//     Use initialized variables instead of getters for styling properties?
//  4. Dependencies on inner variables are not good (in components).
//     Also, it can be reasonable to avoid lots of singletons (e.g., for stateless views) which always live in the memory.
//     Also, it can be reasonable to wrap functional components into classes and separate from their states.
//  5. Simplify WindowWidthMonitor, icons.kt.
//  6. Try to get rid of injectGlobal(...) everywhere as it adds style tags into the head.
//  7. Introduce better modular structure (which should separate resources and style values as well),
//     hide intermediate stuff via single files to emulate something like Java's package-private.
//  8. Remove locale keys unmet in the source code when bundling.

// TODO - deployment and finalization:
//  9. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes.
//  10. Write some custom server with all required configs (caches, routing) and place it here.
//  11. Write about the project's features in the central README.md.
//  12. Change the package name when the project gets its final name.

// TODO:
//  13. Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere.
//  14. Add explanations on the trampoline Rx scheduler in some corresponding SO question.



// Public.

val scaffold = VFC {

    // Global initializations.

    val context = useAppContext()
    AnimationStyles.setContext(context)



    // State.

    val (isHeaderVisible, setHeaderVisible) = useState(true)
    val (headerHeight,    setHeaderHeight)  = useState(0.0)

    val (isMenuOpened, setMenuOpened) = useState(false)
    val hasSlidingMenu = context.screenSize < DESKTOP
    val menuContext = MenuContext(context, hasSlidingMenu, isMenuOpened != usePrevious(isMenuOpened), isMenuOpened)

    // This value doesn't trigger re-rendering on each of its update.
    val lastScrollTop = useRef(.0)



    // Monitors and callbacks.

    val headerRef = useRef<Element>(null)
    useRefHeightMonitor(headerRef) { setHeaderHeight(it) }

    // It's recommended to avoid in-place lambdas for callbacks by some performance reasons:
    // https://youtrack.jetbrains.com/issue/KT-15101
    // At the same time there are no clear explanations about what should be done instead,
    // as even extracted as a separate variable, the lambda is going to be created again on each rendering.
    // These variables should maybe leverage React's memo() or useCallback() somehow,
    // but again it can be not very straightforward.
    val onContentScrolled: UIEventHandler<*> = useCallback(headerHeight, isHeaderVisible) { event ->
        val currentScrollTop = event.currentTarget.scrollTop
        lastScrollTop.current?.let {
            val scrollDelta = currentScrollTop - it
            lastScrollTop.current = currentScrollTop
            val shouldHeaderBeVisible = currentScrollTop <= headerHeight || scrollDelta < 0
            if (shouldHeaderBeVisible != isHeaderVisible) {
                setHeaderVisible(shouldHeaderBeVisible)
            }
        }
    }

    val onScrimClicked: MouseEventHandler<*> = useCallback(isMenuOpened) {
        if (isMenuOpened) {
            setMenuOpened(false)
        }
    }



    // Rendering.

    // Default styles and context.
    +div(ScaffoldStyles.root.name) {

        // This may be counter-intuitive, but only the root is actually scrolled, not the contents block:
        // even having a separate menu block, it just states its positioning for the contents
        // and doesn't have its personal scroll inside the same level of the elements hierarchy.
        onScroll = onContentScrolled

        // Wrapper for a header with the sliding logic.
        +div(ScaffoldStyles.slidingHeader(isHeaderVisible).name) {
            ref = headerRef
            header {
                this.hasSlidingMenu = hasSlidingMenu
                onMenuClick         = { setMenuOpened(!isMenuOpened) }
            }
        }

        // A wrapper for all relative blocks aligned with each other.
        +div(ScaffoldStyles.alignedBlocks.name) {

            // Menu container - just adds the required spacing to position the contents.
            +div(ScaffoldStyles.menuContainer(!hasSlidingMenu).name) {

                // The actual menu with any positioning regardless from its container.
                +aside(ScaffoldStyles.menu(menuContext).name) { menu() }

                // A darkening area covering all contents when the menu is opened.
                +div(ScaffoldStyles.menuCoveringScrim(menuContext).name) {
                    onClick = onScrimClicked
                }

            }

            // Content block.
            +div(ScaffoldStyles.mainContent(context).name) {
                content {}
                footer {}
            }

        }

    }

}



// Private.

private class MenuContext(
    val context: Context,
    val isSliding: Boolean,
    val isTransiting: Boolean,
    val isOpened: Boolean,
) : HasCssSuffix {

    override val cssSuffix: String
        get() = context.cssSuffix +
                (if (isSliding)    "Sliding"      else "Persistent") +
                (if (isTransiting) "TransitingTo" else "Idle") +
                (if (isOpened)     "Opened"       else "Closed")

}

private object ScaffoldStyles : DynamicStyleSheet() {

    val root: NamedRuleSet by css {
        +FontStyles.regular.rules
        fontSize = 100.pct
        position = Position.absolute
        inset(0.px)
        overflow = Overflow.scroll
    }

    val slidingHeader: DynamicCssProvider<Boolean> by dynamicCss {
        +ShadowStyles.defaultShadow.rules
        position = Position.sticky // providing relative spacing for neighbour elements but allowing to scroll over it
        top      = 0.px            // sticking to the top
        overflow = Overflow.hidden // a temp measure to avoid empty spaces when shrinking the header
        zIndex   = 1
        width    = 100.pct
        transform {
            translateY(if (it) 0.pct else (-100).pct)
        }
        transition(
            ::transform,
            StyleValues.time.ms300,
            StyleValues.timing.cubicBezier1,
            if (it) StyleValues.time.ms300 else StyleValues.time.immediate
        )
        opacity = if (it) StyleValues.opacities.full else StyleValues.opacities.transparent
        transition(
            ::opacity,
            StyleValues.time.immediate,
            StyleValues.timing.ease,
            StyleValues.time.ms300
        )
    }

    val alignedBlocks: NamedRuleSet by css {
        display       = Display.flex      // to align child elements relatively
        flexDirection = FlexDirection.row // in a row
    }

    val menuContainer: DynamicCssProvider<Boolean> by dynamicCss {
        if (it) {
            width      = StyleValues.sizes.absolute280
            flexShrink = .0
        }
    }

    val menu: DynamicCssProvider<MenuContext> by dynamicCss {

        // Basic positioning.
        position = Position.fixed
        top      = 0.px
        left     = 0.px
        bottom   = 0.px
        width    = StyleValues.sizes.absolute280

        // Sliding appearance for smaller screens.
        if (it.isSliding) {

            zIndex = 3
            transform {
                translateX(if (it.isOpened) 0.pct else (-100).pct)
            }
            if (it.isOpened) {
                +ShadowStyles.complexShadow.rules
            }

            // Transition animations are enabled only when the menu opens or closes.
            if (it.isTransiting) {
                +TransitionStyles.fastTransition(::transform).rules
                +TransitionStyles.fastTransition(::boxShadow).rules
            }

        }

    }

    val menuCoveringScrim: DynamicCssProvider<MenuContext> by dynamicCss {

        // Basic positioning and styling.
        position = Position.fixed
        inset(0.px)
        zIndex = 2
        backgroundColor = Theme.palette.scrim(it.context)

        // Styling for the hidden state.
        if (!it.isOpened || !it.isSliding) {
            opacity = StyleValues.opacities.transparent
            pointerEvents = PointerEvents.none
        }

        // Transition animation when the menu opens or closes.
        if (it.isTransiting) {
            +TransitionStyles.fastTransition(::opacity).rules
        }

    }

    val mainContent: DynamicCssProvider<Context> by dynamicCss {
        flexGrow = 1.0
        backgroundColor = Theme.palette.surface2(it)
    }

}
