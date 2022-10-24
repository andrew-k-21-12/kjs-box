package io.github.andrewk2112.md.components.header

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.hooks.useRefHeightMonitor
import io.github.andrewk2112.md.resources.endpoints.MaterialDesignEndpoint
import io.github.andrewk2112.md.styles.ShadowStyles
import kotlinx.css.*
import kotlinx.css.properties.transform
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateY
import org.w3c.dom.Element
import react.*
import react.dom.html.ReactHTML.div

// Public.

external interface HeaderProps : Props {
    var isVisible: Boolean
    var onHeightChanged: (Double) -> Unit
}

val header = FC<HeaderProps> { props ->

    // State, callbacks logic.

    val context                = useAppContext()
    val localizator            = useLocalizator()
    val materialDesignEndpoint by useState { MaterialDesignEndpoint() }
    val headerRef              = useRef<Element>(null)
    useRefHeightMonitor(headerRef, props.onHeightChanged)



    // Rendering.

    // Wrapper with sliding logic.
    withClassName(div, HeaderStyles.slidingHeader(props.isVisible).name) {

        ref = headerRef

        headerNotification(
            context,
            localizator("md.theLatestVersionOfMaterialDesignIsNowAvailableForAndroid"),
            localizator("md.learnAboutMaterial3sNewFeatures"),
            localizator("md.startExploringMaterial3"),
            materialDesignEndpoint.designEndpoint
        )

        headerNavigation {}

    }

}



// Private.

private object HeaderStyles : DynamicStyleSheet() {

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

}
