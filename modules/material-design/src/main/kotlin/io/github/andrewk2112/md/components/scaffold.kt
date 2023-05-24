package io.github.andrewk2112.md.components

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Context.ScreenSize.DESKTOP
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.hooks.usePrevious
import io.github.andrewk2112.hooks.useRefHeightMonitor
import io.github.andrewk2112.md.components.content.*
import io.github.andrewk2112.md.components.footer.footer
import io.github.andrewk2112.md.components.header.headerScaffold
import io.github.andrewk2112.md.components.menu.menu
import io.github.andrewk2112.md.styles.AnimationStyles
import io.github.andrewk2112.md.styles.FontStyles
import io.github.andrewk2112.md.styles.ShadowStyles
import io.github.andrewk2112.md.styles.TransitionStyles
import io.github.andrewk2112.resourcewrappers.locales.materialdesign.namespace
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
import web.dom.Element

// TODO - optimizations and modularization:
//  1. Suggest styles (classes) wrapping to avoid the direct usage of kotlin-styled-next (another proposal and PR).
//  2. Use the latest version of the kotlin-styled-next with my PR (drop my stylesheets package).
//  3. Optimize dynamic CSS holders somehow: now they are storing common styles in a duplicating manner,
//     maybe it is even better to drop this idea and use a combination of the React context and media queries?
//     Use initialized or inline variables instead of getters for styling properties (and maybe other stuff - components?)?
//  4. Dependencies on inner variables are not good (in components).
//     Also, it can be reasonable to avoid lots of singletons (e.g., for stateless views) which always live in the memory.
//     Also, it can be reasonable to wrap functional components into classes and separate from their states.
//  5. Simplify WindowWidthMonitor.
//  6. Try to get rid of injectGlobal(...) everywhere as it adds style tags into the head.
//  7. Introduce better modular structure (which should separate resources and style values as well);
//     one Gradle plugin should configure all resource wrappers generators and set the dependency on the core module.
//  8. Maybe the localization API should be changed slightly?
//  9. Drop reducers?
//  10. Replace buildSrc with includeBuild, use TOML-catalogs for all versions, type-safe project dependencies.
//  11. Use Kotlin source files with @Language("kotlin") instead of txt templates.

// TODO - deployment and finalization:
//  1. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes
//     (think more about it as hashes for resources can be helpful when deploying to avoid their unavailability on upd.).
//  2. Write some custom server with all required configs (caches, routing) and place it here.
//  3. Write about the project's features in the central README.md.
//  4. Change the package name when the project gets its final name.

// TODO:
//  1. Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere.
//  2. Add explanations on the trampoline Rx scheduler in some corresponding SO question.



// Public.

val scaffold = VFC {

    useLocalizator(namespace) // lazily loading all translations of the module
    val context = useGlobalInitializations()

    // Sometimes it's barely possible to create single source of truth UI states,
    // because lots of ways to do optimizations will become impossible.
    var isHeaderVisible by useState(true)
    var isMenuOpened    by useState(false)

    // A context providing the exhaustive information to render the menu.
    val menuContext = MenuContext(context, isTransiting = isMenuOpened != usePrevious(isMenuOpened), isMenuOpened)

    // UI states strictly bound to views and derived from their updates only.
    val headerRef     = useRef<Element>(null)
    val headerHeight  = useRefHeightMonitor(headerRef)
    val lastScrollTop = useRef(0.0) // this value doesn't trigger re-rendering on each of its updates

    // https://youtrack.jetbrains.com/issue/KT-15101 advises to avoid in-place lambdas for callbacks due to performance.
    // But in fact Kotlin/JS just treats a reference to a function as not equal to itself (::fun1 == ::fun1 is false).
    // Even if lambdas are declared as separate variables inside FCs, their new instances are created on each rendering.
    // To avoid such pointless instantiations, it's possible to leverage React's memo(), useCallback() and useRef().
    val onContentScroll = useContentScrollCallback(
        headerHeight, isHeaderVisible,
        lastScrollTopProvider = { lastScrollTop.current ?: 0.0 },
        setLastScrollTop      = { lastScrollTop.current = it },
        setHeaderVisible      = { isHeaderVisible = it }
    )
    val onScrimClick: MouseEventHandler<*> = useCallback { isMenuOpened = false }

    root(onContentScroll) {
        slidingHeader(headerRef, isHeaderVisible, hasCloseableMenu = menuContext.isCloseable) { isMenuOpened = it }
        alignedBlocks {
            sideMenu(menuContext, onScrimClick)
            contents(context)
        }
    }

}



// Effects.

private fun useGlobalInitializations(): Context = useAppContext().also { AnimationStyles.setContext(it) }

/**
 * Sets the header visible when the content is getting scrolled up,
 * or when the content's scroll value is less than the header's height.
 */
private inline fun useContentScrollCallback(
    headerHeight: Double,
    isHeaderVisible: Boolean,
    crossinline lastScrollTopProvider: () -> Double,
    crossinline setLastScrollTop: (Double)  -> Unit,
    crossinline setHeaderVisible: (Boolean) -> Unit,
): UIEventHandler<*> = useCallback(headerHeight, isHeaderVisible) { event ->

    // Preparing scroll values.
    val currentScrollTop       = event.currentTarget.scrollTop
    val scrollDelta            = currentScrollTop - lastScrollTopProvider.invoke()
    val isScrollLessThanHeader = currentScrollTop <= headerHeight

    // Resetting the last scroll top value while the scroll direction corresponds to the intended header visibility.
    if (!isHeaderVisible && scrollDelta > 0 || isHeaderVisible && scrollDelta < 0) {
        setLastScrollTop(currentScrollTop)
    }

    // Updating the header visibility if needed.
    when {
        isHeaderVisible  && (!isScrollLessThanHeader && scrollDelta >  headerHeight) -> setHeaderVisible(false)
        !isHeaderVisible && (isScrollLessThanHeader  || scrollDelta < -headerHeight) -> setHeaderVisible(true)
    }

}



// Components.

/**
 * Applies default styles and context, setups the root layout.
 *
 * @param onScroll This may be counter-intuitive, but only the root is actually scrolled, not the contents block.
 * @param children All child elements to be included inside the root.
 */
private inline fun ChildrenBuilder.root(
    noinline onScroll: UIEventHandler<*>,
    crossinline children: ChildrenBuilder.() -> Unit,
) =
    +div(ScaffoldStyles.root.name) {
        this.onScroll = onScroll
        children()
    }

/**
 * The header with the sliding logic.
 */
private inline fun ChildrenBuilder.slidingHeader(
    ref: RefObject<Element>,
    isVisible: Boolean,
    hasCloseableMenu: Boolean,
    crossinline setMenuOpened: (Boolean) -> Unit,
) =
    +div(ScaffoldStyles.slidingHeader(isVisible).name) {
        this.ref = ref
        headerScaffold {
            this.hasCloseableMenu = hasCloseableMenu
            onMenuToggle          = { setMenuOpened(true) }
        }
    }

/**
 * Wraps all relative blocks aligned with each other.
 */
private inline fun ChildrenBuilder.alignedBlocks(crossinline children: ChildrenBuilder.() -> Unit) =
    +div(ScaffoldStyles.alignedBlocks.name, block = children)

private fun ChildrenBuilder.sideMenu(context: MenuContext, onScrimClick: MouseEventHandler<*>) =
    +div(ScaffoldStyles.sideMenuContainer(!context.isCloseable).name) { // the required layout space to be taken
        +aside(ScaffoldStyles.sideMenu(context).name) { menu() } // the actual menu positioned regardless the container
        +div(ScaffoldStyles.contentScrim(context).name) { // a darkening area covering all contents behind the menu
            onClick = onScrimClick
        }
    }

private fun ChildrenBuilder.contents(context: Context) =
    +div(ScaffoldStyles.contents(context).name) {
        contentScaffold {}
        footer {}
    }



// Styles.

private class MenuContext private constructor(
    val context: Context,
    val isCloseable: Boolean,
    val isTransiting: Boolean,
    val isOpened: Boolean,
) : HasCssSuffix {

    constructor(
        context: Context,
        isTransiting: Boolean,
        isOpened: Boolean
    ) : this(context, isCloseable = context.screenSize < DESKTOP, isTransiting, isOpened)

    override val cssSuffix: String
        get() = context.cssSuffix +
                (if (isCloseable)  "Closeable"    else "Persistent") +
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
        width    = 100.pct
        zIndex   = 1
        transform {
            translateY(if (it) 0.pct else (-100).pct)
        }
        transition(
            ::transform,
            StyleValues.time.ms300,
            StyleValues.timing.cubicBezier1,
            StyleValues.time.immediate
        )
        opacity = if (it) StyleValues.opacities.full else StyleValues.opacities.transparent
        transition(
            ::opacity,
            StyleValues.time.immediate,
            StyleValues.timing.ease,
            if (it) StyleValues.time.immediate else StyleValues.time.ms300
        )
    }

    val alignedBlocks: NamedRuleSet by css {
        display       = Display.flex      // to align child elements relatively
        flexDirection = FlexDirection.row // in a row
    }

    val sideMenuContainer: DynamicCssProvider<Boolean> by dynamicCss {
        if (it) {
            width      = StyleValues.sizes.absolute280
            flexShrink = .0
        }
    }

    val sideMenu: DynamicCssProvider<MenuContext> by dynamicCss {

        // Basic positioning.
        position = Position.fixed
        top      = 0.px
        left     = 0.px
        bottom   = 0.px
        width    = StyleValues.sizes.absolute280

        // Open-close-supporting appearance for smaller screens.
        if (it.isCloseable) {

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

    val contentScrim: DynamicCssProvider<MenuContext> by dynamicCss {

        // Basic positioning and styling.
        position = Position.fixed
        inset(0.px)
        zIndex = 2
        backgroundColor = Theme.palette.scrim(it.context)

        // Styling for the hidden state.
        if (!it.isOpened || !it.isCloseable) {
            opacity = StyleValues.opacities.transparent
            pointerEvents = PointerEvents.none
        }

        // Transition animation when the menu opens or closes.
        if (it.isTransiting) {
            +TransitionStyles.fastTransition(::opacity).rules
        }

    }

    val contents: DynamicCssProvider<Context> by dynamicCss {
        flexGrow = 1.0
        backgroundColor = Theme.palette.surface2(it)
    }

}
