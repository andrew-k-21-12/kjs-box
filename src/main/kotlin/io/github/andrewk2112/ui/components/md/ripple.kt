package io.github.andrewk2112.ui.components.md

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.*
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.*
import org.w3c.dom.Element
import org.w3c.dom.get
import react.RBuilder
import react.dom.*
import react.dom.events.MouseEventHandler
import react.dom.events.TouchEventHandler
import styled.animation
import kotlin.math.max

// Public.

fun RBuilder.ripple(context: Context, classes: String? = null, children: RBuilder.() -> Unit) {

    // For touch devices.
    val onTouchStart: TouchEventHandler<*> = { event ->
        event.touches[0]?.let { launchRippleAnimation(context, event.currentTarget, it.clientX, it.clientY) }
    }

    // For desktops (mouse-compatible devices).
    val onMouseDown: MouseEventHandler<*> = {
        if (it.isLeftButton) {
            launchRippleAnimation(context, it.currentTarget, it.clientX.toInt(), it.clientY.toInt())
        }
    }

    // Contains children elements and launches ripple animations inside.
    div("${classes ?: ""} ${RippleStyles.container.name}") {

        attrs {
            this.onTouchStart = onTouchStart
            this.onMouseDown  = onMouseDown
        }

        children()

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

    val container by css {
        position = Position.relative // to make a ripple animation position correctly
        overflow = Overflow.hidden   // to prevent a ripple animation from getting outside the bounds
    }

    val rippleAnimation by dynamicCss<Context> {
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
