package io.github.andrewk2112.md.components

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.md.components.content.content
import io.github.andrewk2112.md.components.header.header
import io.github.andrewk2112.md.components.menu.menu
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div

// Public.

val materialDesign = FC<Props> {

    // State.

    val (isHeaderVisible, setHeaderVisible) = useState(true)
    val (headerHeight,    setHeaderHeight)  = useState(0.0)

    val onContentScrolled = { scrollTop: Double, deltaY: Double ->
        val shouldHeaderBeVisible = scrollTop <= headerHeight || deltaY < 0
        if (shouldHeaderBeVisible != isHeaderVisible) {
            setHeaderVisible(shouldHeaderBeVisible)
        }
    }



    // Rendering.

    // Default styles and context.
    withClassName(div, MaterialDesignStyles.root.name) {

        // Sliding header.
        header {
            isVisible       = isHeaderVisible
            onHeightChanged = { setHeaderHeight(it) }
        }

        // A wrapper for all relative blocks aligned with each other.
        withClassName(div, MaterialDesignStyles.alignedBlocks.name) {

            // Menu block.
            menu { this.headerHeight = headerHeight }

            // Content block.
            content {
                topSpacing = headerHeight
                onScrolled = onContentScrolled
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

    val alignedBlocks: NamedRuleSet by css {
        display       = Display.flex      // to align child elements relatively
        flexDirection = FlexDirection.row // in a row
        position = Position.absolute // to occupy the entire available window space
        inset(0.px)
    }

}
