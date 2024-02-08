package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components

import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.extensions.transition
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.core.hooks.usePrevious
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useRefHeightMonitor
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.footer.footer
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.headerScaffold
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.menu.menu
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.namespace
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.HasCssSuffix
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.DESKTOP
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.MaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.provideMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
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

// TODO - optimizations and modularization:
//  1. Suggest styles (classes) wrapping to avoid the direct usage of kotlin-styled-next (another proposal and PR).
//  2. Use the latest version of the kotlin-styled-next with my PR (drop my stylesheets package).
//  3. Optimize dynamic CSS holders somehow: now they are storing common styles in a duplicating manner,
//     maybe it is even better to drop this idea and use a combination of the React context and media queries?
//     Use initialized or inline variables instead of getters for styling properties (and maybe other stuff - components?)?
//  4. Dependencies on inner variables are not good (in components).
//     Also, it can be reasonable to avoid lots of singletons (e.g., for stateless views) which always live in the memory.
//     Also, it can be reasonable to wrap functional components into classes and separate from their states.
//  5. Try to get rid of injectGlobal(...) everywhere as it adds style tags into the head.
//  6. Maybe the localization API should be changed slightly (for better generalization)?
//  7. Split current big Gradle modules into fine-grained ones, make the modules structure flatter.

// TODO - deployment and finalization:
//  1. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes
//     (think more about it as hashes for resources can be helpful when deploying to avoid their unavailability on upd.).
//  2. Write some custom server with all required configs (caches, routing) and place it here.
//  3. Write about the project's features in the central README.md.
//  4. Publish the Gradle plugins providing this project's features.

// TODO:
//  1. Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere.
//  2. Add explanations on the trampoline Rx scheduler in some corresponding SO question.



// Public.

val dependencyInjectionAndScaffold = FC {
    val rootComponent = useRootComponent()
    rootComponent.getLocalizationEngine().useLocalizator(namespace) // lazily loading all translations of the module,
                                                                    // the order matters to avoid unneeded re-rendering
    val component = useMemo(rootComponent) { MaterialDesignComponent(rootComponent) }
    provideMaterialDesignComponent(component) { // providing the dependency injection component
                                                // for all nested React components
        scaffold()
    }
}



// Components.

/** This component is quite important to decouple its rendering from [dependencyInjectionAndScaffold]. */
private val scaffold = FC {

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

    root(context, styles, onContentScroll) {
        slidingHeader(
            styles,
            headerRef,
            isHeaderVisible,
            hasCloseableMenu = menuContext.isCloseable
        ) {
            isMenuOpened = it
        }
        alignedBlocks(styles) {
            sideMenu(menuContext, styles, onScrimClick)
            contents(context, styles)
        }
    }

}

/**
 * Applies default styles and context, setups the root layout.
 *
 * @param onScroll This may be counter-intuitive, but only the root is actually scrolled, not the contents block.
 * @param children All child elements to be included inside the root.
 */
private inline fun ChildrenBuilder.root(
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
private inline fun ChildrenBuilder.slidingHeader(
    styles: ScaffoldStyles,
    ref: RefObject<Element>,
    isVisible: Boolean,
    hasCloseableMenu: Boolean,
    crossinline setMenuOpened: (Boolean) -> Unit,
) =
    +div(clazz = styles.slidingHeader(isVisible).name) {
        this.ref = ref
        headerScaffold {
            this.hasCloseableMenu = hasCloseableMenu
            onMenuToggle          = { setMenuOpened(true) }
        }
    }

/**
 * Wraps all relative blocks aligned with each other.
 */
private inline fun ChildrenBuilder.alignedBlocks(
    styles: ScaffoldStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.alignedBlocks.name, children)

private fun ChildrenBuilder.sideMenu(context: MenuContext, styles: ScaffoldStyles, onScrimClick: MouseEventHandler<*>) =
    +div(clazz = styles.sideMenuContainer(!context.isCloseable).name) { // the required layout space to be taken
        +aside(clazz = styles.sideMenu(context).name) { menu() } // the actual menu positioned regardless the container
        +div(clazz = styles.contentScrim(context).name) { // a darkening area covering all contents behind the menu
            onClick = onScrimClick
        }
    }

private fun ChildrenBuilder.contents(context: Context, styles: ScaffoldStyles) =
    +div(clazz = styles.contents(context).name) {
        contentScaffold {}
        footer {}
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
