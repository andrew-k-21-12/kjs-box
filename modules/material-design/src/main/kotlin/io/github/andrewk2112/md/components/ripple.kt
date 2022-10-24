package io.github.andrewk2112.md.components

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.*
import io.github.andrewk2112.hooks.useAppContext
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.*
import org.w3c.dom.Element
import org.w3c.dom.get
import react.*
import react.dom.DOMProps
import react.dom.events.MouseEventHandler
import react.dom.events.TouchEventHandler
import react.dom.html.ReactHTML.div
import styled.animation
import kotlin.math.max

// Public.

/** A wrapper to provide the ripple touch effect. */
val ripple = FC<DOMProps> { props ->

    // State and persistent handlers.
    val context = useAppContext()
    val (onTouchStart, onMouseDown) = useState {
        Pair<TouchEventHandler<*>, MouseEventHandler<*>>(
            // For touch devices.
            { event ->
                event.touches[0]?.let { launchRippleAnimation(context, event.currentTarget, it.clientX, it.clientY) }
            },
            // For desktops (mouse-compatible devices).
            {
                if (it.isLeftButton) {
                    launchRippleAnimation(context, it.currentTarget, it.clientX.toInt(), it.clientY.toInt())
                }
            }
        )
    }.component1()

    // Contains children elements and launches ripple animations inside.
    withClassName(div, props.className.toString(), RippleStyles.container.name) {

        this.onTouchStart = onTouchStart
        this.onMouseDown  = onMouseDown

        +props.children

    }

}



// Private.

private fun launchRippleAnimation(context: Context, target: Element, targetX: Int, targetY: Int) {

    // Preparing sizes.
    val diameter = max(target.clientWidth, target.clientHeight)
    val radius   = diameter / 2

    // Cleaning up a previous animation element.
    val animationClassName = RippleStyles.rippleAnimation(context).name
    target.getElementsByClassName(animationClassName)[0]?.remove()

    // Creating, configuring and appending an animation element.
    document.createElement("span").apply {
        setStyle {
            width  = diameter.px
            height = diameter.px
            left   = (targetX - target.offsetLeft - radius).px
            top    = (targetY - target.offsetTop  - radius + (target.parentElement?.scrollTop?.toInt() ?: 0)).px
        }
        classList.add(animationClassName)
        target.appendChild(this)
    }

}

private object RippleStyles : DynamicStyleSheet() {

    val container: NamedRuleSet by css {
        position = Position.relative // to make a ripple animation position correctly
        overflow = Overflow.hidden   // to prevent a ripple animation from getting outside the bounds
    }

    val rippleAnimation: DynamicCssProvider<Context> by dynamicCss {
        position = Position.absolute
        borderRadius = 50.pct // to make it absolutely round
        transform { scale(0) }
        animation(duration = StyleValues.time.ms600, timing = StyleValues.timing.linear, builder = {
            to {
                transform { scale(4) }
                opacity = 0
            }
        })
        backgroundColor = Theme.palette.selectionFocused1(it)
        pointerEvents = PointerEvents.none // to avoid drag glitches
    }

}
