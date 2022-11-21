package io.github.andrewk2112.md.components

import csstype.px as pixels
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.md.components.common.horizontalDivider
import io.github.andrewk2112.md.components.content.*
import io.github.andrewk2112.md.components.header.header
import io.github.andrewk2112.md.components.menu.menu
import io.github.andrewk2112.md.styles.AnimationStyles
import io.github.andrewk2112.md.styles.FontStyles
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import kotlinx.css.*
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.Overflow
import kotlinx.css.Position
import kotlinx.css.pct
import kotlinx.js.jso
import react.*
import react.dom.events.WheelEventHandler
import react.dom.html.ReactHTML.div

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
//     Add explanations on the trampoline Rx scheduler in some corresponding SO question.

// TODO:
//  6. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes.
//  7. Write some custom server with all required configs (caches, routing) and place it here.
//  8. Introduce better modular structure (which should separate resources and style values as well).
//  9. Write about the project's features in the central README.md.
//  10. Change the package name when the project gets its final name.
//  11. Remove locale keys unmet in the source code when bundling.



// Public.

val materialDesign = FC<Props> {

    // Global initializations.

    val context = useAppContext()
    AnimationStyles.initialize { context }



    // State.

    val (isHeaderVisible, setHeaderVisible) = useState(true)
    val (headerHeight,    setHeaderHeight)  = useState(0.0)

    val onContentScrolled: WheelEventHandler<*> = {
        val shouldHeaderBeVisible = it.currentTarget.scrollTop <= headerHeight || it.deltaY < 0
        if (shouldHeaderBeVisible != isHeaderVisible) {
            setHeaderVisible(shouldHeaderBeVisible)
        }
    }



    // Rendering.

    // Default styles and context.
    +div(MaterialDesignStyles.root.name) {

        // Sliding header.
        header {
            isVisible       = isHeaderVisible
            onHeightChanged = { setHeaderHeight(it) }
        }

        // A wrapper for all relative blocks aligned with each other.
        +div(MaterialDesignStyles.alignedBlocks.name) {

            // Menu block.
            menu { this.headerHeight = headerHeight }

            // Content block.
            +div(MaterialDesignStyles.mainContent(context).name) {

                onWheel = onContentScrolled

                // Top spacing to fit the header.
                +div(MaterialDesignStyles.headerSpacer.name) {
                    style = jso {
                        height = headerHeight.pixels
                    }
                }

                // Content sections.
                contentDesign {}
                contentWhatsNew {}
                +horizontalDivider(MaterialDesignStyles.divider.name)
                contentMaterialArticles {}
                contentMaterialStudies {}

                footer {}

            }

        }

    }

}



// Private.

private object MaterialDesignStyles : DynamicStyleSheet() {

    val root: NamedRuleSet by css {
        +FontStyles.regular.rules
        fontSize = 100.pct
    }

    val alignedBlocks: NamedRuleSet by css {
        display       = Display.flex      // to align child elements relatively
        flexDirection = FlexDirection.row // in a row
        position = Position.absolute // to occupy the entire available window space
        inset(0.px)
    }

    val mainContent: DynamicCssProvider<Context> by dynamicCss {
        flexGrow = 1.0
        overflow = Overflow.scroll
        backgroundColor = Theme.palette.surface2(it)
    }

    val headerSpacer: NamedRuleSet by css {}

    val divider: NamedRuleSet by css {
        margin(horizontal = StyleValues.spacing.absolute40)
    }

}
