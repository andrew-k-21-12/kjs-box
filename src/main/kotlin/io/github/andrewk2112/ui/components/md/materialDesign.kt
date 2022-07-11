package io.github.andrewk2112.ui.components.md

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.ui.components.md.content.content
import io.github.andrewk2112.ui.components.md.header.header
import io.github.andrewk2112.ui.components.md.menu.menu
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div

// Public.

val materialDesign = FC<Props> {

    // State.

    val (isHeaderVisible, setHeaderVisible) = useState(true)
    val (headerHeight,    setHeaderHeight)  = useState(0)

    val onContentScrolled = { scrollTop: Double, deltaY: Double ->
        val shouldHeaderBeVisible = scrollTop <= headerHeight || deltaY < 0
        if (shouldHeaderBeVisible != isHeaderVisible) {
            setHeaderVisible(shouldHeaderBeVisible)
        }
    }



    // Rendering.

    withClassName(div, MaterialDesignStyles.root.name) {

        // Sliding header block.
        header {
            isVisible       = isHeaderVisible
            onHeightChanged = { setHeaderHeight(it) }
        }

        // Menu block.
        menu { this.headerHeight = headerHeight }

        // Content block.
        content { onScrolled = onContentScrolled }

    }

}



// Private.

private object MaterialDesignStyles : DynamicStyleSheet() {

    val root: NamedRuleSet by css {
        +StyleValues.fontFaces.roboto.rules
        fontSize = 100.pct
    }

}
