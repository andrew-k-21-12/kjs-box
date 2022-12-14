package io.github.andrewk2112.md.styles

import dom.Element
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.isLeftButton
import io.github.andrewk2112.extensions.setStyle
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import js.core.get
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.scale
import kotlinx.css.properties.transform
import react.dom.DOMAttributes
import react.dom.events.MouseEventHandler
import react.dom.events.TouchEventHandler
import styled.animation
import kotlin.math.max

/**
 * All styled reusable animations in a single place.
 *
 * Don't forget to [initialize] it at some root component!
 */
object AnimationStyles : DynamicStyleSheet() {

    // Public.

    /**
     * Must be called before any usage and on each [Context] update as some animations require the [Context].
     */
    fun setContext(context: Context) {
        this.context = context
    }

    /**
     * Adds the tap highlighting animation to the [DOMAttributes] receiver.
     *
     * Note that:
     * 1. The animation spreads without bounds: make sure the receiver is wrapped into something with [Overflow.hidden].
     * 2. The animation node is added as the preceding node to the same level with the receiver.
     */
    fun DOMAttributes<*>.addTapHighlighting() {
        onTouchStart = tapHighlightingTouchEventHandler
        onMouseDown  = tapHighlightingMouseEventHandler
    }



    // Private - animations' logic.

    /**
     * Launches the ripple animation for the [target] starting from the tap point with [tapX] and [tapY].
     */
    private fun launchRippleAnimation(target: Element, tapX: Double, tapY: Double) {

        // Checking if it's possible to launch the animation at all.
        val parentElement = target.parentElement ?: return

        // Preparing sizes and positions.
        val diameter   = max(target.clientWidth, target.clientHeight)
        val radius     = diameter / 2
        val targetRect = target.getBoundingClientRect()

        // Cleaning up the previous animation elements.
        parentElement
            .querySelectorAll("[class*=${rippleAnimationElement.staticCssSuffix}]")
            .forEach { parentElement.removeChild(it) }

        // Creating and appending the animation element.
        document.createElement("span").apply {
            setStyle {
                width  = diameter.px
                height = diameter.px
                left   = (tapX - targetRect.left - radius).px
                top    = (tapY - targetRect.top  - radius).px
            }
            classList.add(rippleAnimationElement(context).name)
            parentElement.prepend(this)
        }

    }

    /** The latest actual [Context] - any of the [Context] update must be set by [setContext]. */
    private lateinit var context: Context

    /** [MouseEventHandler] to launch the animation - for desktop (mouse-compatible) devices. */
    private val tapHighlightingMouseEventHandler: MouseEventHandler<*> = {
        if (it.isLeftButton) {
            launchRippleAnimation(it.currentTarget, it.clientX, it.clientY)
        }
    }

    /** [TouchEventHandler] to launch the animation - for touch devices. */
    private val tapHighlightingTouchEventHandler: TouchEventHandler<*> = { event ->
        event.touches[0].apply {
            launchRippleAnimation(event.currentTarget, clientX, clientY)
        }
    }



    // Private - styles.

    private val rippleAnimationElement: DynamicCssProvider<Context> by dynamicCss {
        position = Position.absolute
        borderRadius = 50.pct // to make it absolutely round
        transform { scale(0) }
        animation(duration = StyleValues.time.ms600, timing = StyleValues.timing.linear, builder = {
            to {
                transform { scale(4) }
                opacity = StyleValues.opacities.transparent
            }
        })
        backgroundColor = Theme.palette.selectionActive1(it)
        pointerEvents = PointerEvents.none // to avoid intercepting taps
    }

}
