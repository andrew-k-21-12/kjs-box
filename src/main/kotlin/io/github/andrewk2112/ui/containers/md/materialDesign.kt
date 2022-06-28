package io.github.andrewk2112.ui.containers.md

import csstype.ClassName
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.ui.components.md.notificationHeader
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.ui.components.md.menu.MenuBottomSpacing.*
import io.github.andrewk2112.ui.components.md.menu.menuCategory
import io.github.andrewk2112.ui.components.md.menu.menuDivider
import io.github.andrewk2112.ui.components.md.menu.menuItem
import io.github.andrewk2112.ui.materialDesignLogo
import io.github.andrewk2112.ui.styles.*
import kotlinx.css.*
import kotlinx.css.properties.*
import org.w3c.dom.Element
import org.w3c.dom.ResizeObserver
import react.*
import react.dom.*
import react.dom.events.WheelEventHandler
import styled.css
import styled.styledSpan

// TODO:
//  0. Suggest styles wrapping to avoid direct usage of kotlin-styled-next (another proposal and PR)!
//     Use the latest version of the kotlin-styled-next with my PR (drop my stylesheets package and AspectRatio extension).
//  1. Optimize dynamic CSS holders somehow: now they are storing common styles in a duplicating manner.

// TODO:
//  2. Dependencies on inner variables are not good (in components).
//     Also, it can be reasonable to avoid lots of singletons (e.g., for stateless views) which always live in the memory.
//     Also, it can be reasonable to wrap functional components into classes and separate from their states.
//  3. Simplify WindowWidthMonitor, icons.kt.
//  4. Use FC instead of fc where possible.
//  5. Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere!

// TODO:
//  6. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes.
//  7. Fix webpack warnings and do a clean-up for its scripts.
//  8. Write some custom server with all required configs (caches, routing) and place it here.
//  9. Extract a module with a framework itself.



// Public.

val materialDesign = fc<Props> {

    // State.

    val context     = useAppContext()
    val localizator = useLocalizator()

    val refHeader  = useRef<Element>(null)
    val refContent = useRef<Element>(null)

    val (isHeaderVisible, setHeaderVisible) = useState(true)
    val (headerHeight,    setHeaderHeight)  = useState(0)

    // Evaluating the header's state.
    val onContentWheel: WheelEventHandler<*> = {
        val headerClientHeight = refHeader.current?.clientHeight
        val contentScrollTop   = refContent.current?.scrollTop
        if (headerClientHeight != null && contentScrollTop != null) {
            val shouldHeaderBeVisible = contentScrollTop <= headerClientHeight || it.deltaY < 0
            if (shouldHeaderBeVisible != isHeaderVisible) {
                setHeaderVisible(shouldHeaderBeVisible)
            }
        }
    }

    // Starting to monitor the header's height.
    useEffectOnce {
        val resizeObserver = refHeader.current?.let { element ->
            ResizeObserver { entries, _ ->
                val newHeaderHeight = entries.firstOrNull()?.target?.clientHeight
                if (newHeaderHeight != null && newHeaderHeight != headerHeight) {
                    setHeaderHeight(newHeaderHeight)
                }
            }.also { it.observe(element) }
        }
        cleanup { resizeObserver?.disconnect() }
    }



    // Rendering.

    div(MaterialDesignStyles.root.name) {

        // The entire sliding header.
        div(MaterialDesignStyles.slidingHeader(isHeaderVisible).name) {

            attrs { ref = refHeader }

            notificationHeader(
                context,
                localizator("md.theLatestVersionOfMaterialDesignIsNowAvailableForAndroid"),
                localizator("md.learnAboutMaterial3sNewFeatures"),
                localizator("md.startExploringMaterial3"),
                ENDPOINT_TO_MATERIAL_3
            )

            navigationHeader {}

        }

        // Menu block.
        div(MaterialDesignStyles.menu(context).name) {

            // Menu header - below the sliding header.
            val menuHeaderClasses = MaterialDesignStyles.menuHeaderAppearance(context).name + " " +
                                    MaterialDesignStyles.menuHeaderSize(headerHeight).name
            div(menuHeaderClasses) {

                // Material icon.
                materialDesignLogo.component {
                    attrs.className = ClassName(MaterialDesignStyles.menuHeaderIcon.name)
                }

                // Material label.
                span(MaterialDesignStyles.menuHeaderLabel(context).name) {
                    +localizator("md.materialDesign").uppercase()
                }

            }

            div(MaterialDesignStyles.menuItems.name) {



                // TODO: Extract the whole menu from this file.
                menuCategory(context, localizator("md.materialSystem"))
                menuItem(context, MEDIUM, localizator("md.introduction"), "https://material.io/design/introduction")
                menuItem(context, MEDIUM, localizator("md.materialStudies"), "https://material.io/design/material-studies/about-our-material-studies")
                menuDivider(context)
                menuCategory(context, localizator("md.materialFoundation"))
                menuItem(context, MEDIUM, localizator("md.foundationOverview"), "https://material.io/design/foundation-overview")
                menuItem(context, SMALL, localizator("md.environment"), "https://material.io/design/environment/surfaces")
                menuItem(context, SMALL, localizator("md.layout"), "https://material.io/design/layout/understanding-layout")
                menuItem(context, SMALL, localizator("md.navigation"), "https://material.io/design/navigation/understanding-navigation")
                menuItem(context, SMALL, localizator("md.color"), "https://material.io/design/color/the-color-system")
                menuItem(context, SMALL, localizator("md.typography"), "https://material.io/design/typography/the-type-system")
                menuItem(context, SMALL, localizator("md.sound"), "https://material.io/design/sound/about-sound")
                menuItem(context, SMALL, localizator("md.iconography"), "https://material.io/design/iconography/product-icons")
                menuItem(context, SMALL, localizator("md.shape"), "https://material.io/design/shape/about-shape")
                menuItem(context, SMALL, localizator("md.motion"), "https://material.io/design/motion/understanding-motion")
                menuItem(context, SMALL, localizator("md.interaction"), "https://material.io/design/interaction/gestures")
                menuItem(context, SMALL, localizator("md.communication"), "https://material.io/design/communication/confirmation-acknowledgement")
                menuItem(context, MEDIUM, localizator("md.machineLearning"), "https://material.io/design/machine-learning/understanding-ml-patterns")
                menuDivider(context)
                menuCategory(context, localizator("md.materialGuidelines"))
                menuItem(context, MEDIUM, localizator("md.guidelinesOverview"), "https://material.io/design/guidelines-overview")
                menuItem(context, SMALL, localizator("md.materialTheming"), "https://material.io/design/material-theming/overview")
                menuItem(context, SMALL, localizator("md.usability"), "https://material.io/design/usability/accessibility")
                menuItem(context, BIG, localizator("md.platformGuidance"), "https://material.io/design/platform-guidance/android-bars")



            }

        }

        // Content block.
        div(MaterialDesignStyles.content(context).name) {

            attrs {
                ref     = refContent
                onWheel = onContentWheel
            }



            // TODO: Fill the actual contents.

            styledSpan {
                css { fontSize = 5.rem }
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

}



// Private.

private object MaterialDesignStyles : DynamicStyleSheet() {

    val root: NamedRuleSet by css {
        +StyleValues.fontFaces.roboto.rules
        fontSize = 100.pct
    }

    val slidingHeader: DynamicCssProvider<Boolean> by dynamicCss {
        +ShadowStyles.defaultShadow.rules
        position = Position.absolute
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
        opacity = if (it) 1 else 0
        transition(
            ::opacity,
            StyleValues.time.immediate,
            StyleValues.timing.ease,
            StyleValues.time.ms300
        )
    }

    val menu: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.defaultStroke(StrokeConfigs(it, StrokePosition.RIGHT)).rules
        display       = Display.flex
        flexDirection = FlexDirection.column
        position = Position.absolute
        top      = 0.px
        bottom   = 0.px
        width    = StyleValues.sizes.absolute280
        backgroundColor = Theme.palette.surface2(it)
    }

    val menuHeaderAppearance: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.defaultStroke(StrokeConfigs(it, StrokePosition.BOTTOM)).rules
        flexShrink    = 0.0
        display       = Display.flex
        alignItems    = Align.center
        flexDirection = FlexDirection.row
        backgroundColor = Theme.palette.surface2(it)
    }

    val menuHeaderSize: DynamicCssProvider<Int> by dynamicCss { height = it.px }

    val menuHeaderIcon: NamedRuleSet by css {
        +IconStyles.smallSizedIcon.rules
        marginLeft  = StyleValues.spacing.absolute24
        marginRight = StyleValues.spacing.absolute16
    }

    val menuHeaderLabel: DynamicCssProvider<Context> by dynamicCss {
        fontSize = StyleValues.fontSizes.relativep95
        color    = Theme.palette.onSurface2(it)
    }

    val menuItems: NamedRuleSet by css {
        overflow = Overflow.scroll
    }

    val content: DynamicCssProvider<Context> by dynamicCss {
        position = Position.absolute
        left     = StyleValues.sizes.absolute280
        top      = 0.px
        bottom   = 0.px
        right    = 0.px
        overflow = Overflow.scroll
        backgroundColor = Theme.palette.surface2(it)
    }

}

private const val ENDPOINT_TO_MATERIAL_3 = "https://material.io/design"
