@file:Suppress("FunctionName")

package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.transition
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.*
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.NAMESPACE
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.HasCssSuffix
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.DESKTOP
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.footer.Footer
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.HeaderScaffold
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.menu.Menu
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.MaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.ProvideMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.utility.react.components.FC
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.utility.react.hooks.usePrevious
import io.github.andrewk2112.utility.react.hooks.useRefHeightMonitor
import kotlinx.css.*
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.Overflow
import kotlinx.css.Position
import kotlinx.css.pct
import kotlinx.css.properties.*
import kotlinx.css.properties.transform
import react.*
import react.dom.events.MouseEventHandler
import react.dom.events.UIEventHandler
import react.dom.html.ReactHTML.aside
import react.dom.html.ReactHTML.div
import web.dom.Element



// Public.

val DependencyInjectionAndScaffold by FC {
    val rootComponent = useRootComponent()
    rootComponent.getLocalizationEngine().loadLocalizations(NAMESPACE) // lazily loading all translations of the module,
                                                                       // the order matters to avoid unneeded re-rendering
    val component = useMemo(rootComponent) { MaterialDesignComponent(rootComponent) }
    ProvideMaterialDesignComponent(component) { // providing the dependency injection component
                                                // for all nested React components
        Scaffold()
    }
}



// Components.

/** This component is quite important to decouple its rendering from [DependencyInjectionAndScaffold]. */
private val Scaffold by FC {

    val context = useDesignTokensContext()

    // Retrieving an instance of root DI component, creating a style sheet according to the injected design tokens:
    // these instances are not going to be created again at each new rendering.
    val component = useMaterialDesignComponent()
    val styles    = useMemoWithReferenceCount(component) { ScaffoldStyles(component.getMaterialDesignTokens()) }

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

    Root(context, styles, onContentScroll) {
        SlidingHeader(
            styles,
            headerRef,
            isHeaderVisible,
            hasCloseableMenu = menuContext.isCloseable
        ) {
            isMenuOpened = it
        }
        AlignedBlocks(styles) {
            SideMenu(menuContext, styles, onScrimClick)
            Contents(context, styles)
        }
    }

}

/**
 * Applies default styles and context, setups the root layout.
 *
 * @param onScroll This may be counter-intuitive, but only the root is actually scrolled, not the contents block.
 * @param children All child elements to be included inside the root.
 */
private inline fun ChildrenBuilder.Root(
    context: Context,
    styles: ScaffoldStyles,
    noinline onScroll: UIEventHandler<*>,
    crossinline children: ChildrenBuilder.() -> Unit,
) =
    +div(clazz = styles.root(context).name) {
        this.onScroll = onScroll
        children()
    }

/**
 * The header with the sliding logic.
 */
private inline fun ChildrenBuilder.SlidingHeader(
    styles: ScaffoldStyles,
    ref: RefObject<Element>,
    isVisible: Boolean,
    hasCloseableMenu: Boolean,
    crossinline setMenuOpened: (Boolean) -> Unit,
) =
    +div(clazz = styles.slidingHeader(isVisible).name) {
        this.ref = ref
        HeaderScaffold {
            this.hasCloseableMenu = hasCloseableMenu
            onMenuToggle          = { setMenuOpened(true) }
        }
    }

/**
 * Wraps all relative blocks aligned with each other.
 */
private inline fun ChildrenBuilder.AlignedBlocks(
    styles: ScaffoldStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.alignedBlocks.name, children)

private fun ChildrenBuilder.SideMenu(context: MenuContext, styles: ScaffoldStyles, onScrimClick: MouseEventHandler<*>) =
    +div(clazz = styles.sideMenuContainer(!context.isCloseable).name) { // the required layout space to be taken
        +aside(clazz = styles.sideMenu(context).name) { Menu() } // the actual menu positioned regardless the container
        +div(clazz = styles.contentScrim(context).name) { // a darkening area covering all contents behind the menu
            onClick = onScrimClick
        }
    }

private fun ChildrenBuilder.Contents(context: Context, styles: ScaffoldStyles) =
    +div(clazz = styles.contents(context).name) {
        ContentScaffold {}
        Footer {}
    }



// Effects.

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

private class ScaffoldStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val root: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.regular(it).rules
        fontSize = 100.pct
        position = Position.absolute
        inset    = Inset(0.px)
        overflow = Overflow.scroll
    }

    val slidingHeader: DynamicCssProvider<Boolean> by dynamicCss {
        +materialDesignTokens.component.shadow.default.rules
        position = Position.sticky // providing relative spacing for neighbour elements but allowing to scroll over it
        top      = 0.px            // sticking to the top
        width    = 100.pct
        zIndex   = 1
        transform {
            translateY(if (it) 0.pct else (-100).pct)
        }
        transition(
            ::transform,
            materialDesignTokens.reference.time.ms300,
            materialDesignTokens.reference.timing.cubicBezier1,
            materialDesignTokens.reference.time.immediate
        )
        opacity = materialDesignTokens.reference.opacities.run { if (it) full else transparent }
        transition(
            ::opacity,
            materialDesignTokens.reference.time.immediate,
            materialDesignTokens.reference.timing.ease,
            materialDesignTokens.reference.time.run { if (it) immediate else ms300 }
        )
    }

    val alignedBlocks: NamedRuleSet by css {
        display       = Display.flex      // to align child elements relatively
        flexDirection = FlexDirection.row // in a row
    }

    val sideMenuContainer: DynamicCssProvider<Boolean> by dynamicCss {
        if (it) {
            width      = materialDesignTokens.reference.sizes.absolute280
            flexShrink = .0
        }
    }

    val sideMenu: DynamicCssProvider<MenuContext> by dynamicCss {

        // Basic positioning.
        position = Position.fixed
        top      = 0.px
        left     = 0.px
        bottom   = 0.px
        width    = materialDesignTokens.reference.sizes.absolute280

        // Open-close-supporting appearance for smaller screens.
        if (it.isCloseable) {

            zIndex = 3
            transform {
                translateX(if (it.isOpened) 0.pct else (-100).pct)
            }
            if (it.isOpened) {
                +materialDesignTokens.component.shadow.complex.rules
            }

            // Transition animations are enabled only when the menu opens or closes.
            if (it.isTransiting) {
                +materialDesignTokens.component.transition.fast(::transform).rules
                +materialDesignTokens.component.transition.fast(::boxShadow).rules
            }

        }

    }

    val contentScrim: DynamicCssProvider<MenuContext> by dynamicCss {

        // Basic positioning and styling.
        position = Position.fixed
        inset    = Inset(0.px)
        zIndex   = 2
        backgroundColor = materialDesignTokens.system.palette.scrim(it.context)

        // Styling for the hidden state.
        if (!it.isOpened || !it.isCloseable) {
            opacity = materialDesignTokens.reference.opacities.transparent
            pointerEvents = PointerEvents.none
        }

        // Transition animation when the menu opens or closes.
        if (it.isTransiting) {
            +materialDesignTokens.component.transition.fast(::opacity).rules
        }

    }

    val contents: DynamicCssProvider<Context> by dynamicCss {
        flexGrow = 1.0
        backgroundColor = materialDesignTokens.system.palette.surface2(it)
    }

}
