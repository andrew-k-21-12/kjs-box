package io.github.andrewk2112.md.styles

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.isLeftButton
import io.github.andrewk2112.extensions.setStyle
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.scale
import kotlinx.css.properties.transform
import org.w3c.dom.Element
import org.w3c.dom.get
import react.dom.DOMAttributes
import react.dom.events.MouseEventHandler
import react.dom.events.TouchEventHandler
import styled.animation
import kotlin.math.max

/**
 * All styled reusable animations in a single place.
 *
 * Don't forget to [initialize] it at some root component!
 * */
object AnimationStyles : DynamicStyleSheet() {

    // Public.

    /**
     * Must be called before any other usage to set the [contextProvider] as some animations require the [Context].
     * */
    fun initialize(contextProvider: () -> Context) {
        this.contextProvider = contextProvider
    }

    /**
     * Adds the tap highlighting animation to the [DOMAttributes] receiver.
     *
     * Note that:
     * 1. The animation spreads without bounds: make sure the receiver is wrapped into something with [Overflow.hidden].
     * 2. The animation node is added as the preceding node to the same level with the receiver.
     * */
    fun DOMAttributes<*>.addTapHighlighting() {
        onTouchStart = tapHighlightingTouchEventHandler
        onMouseDown  = tapHighlightingMouseEventHandler
    }



    // Private - animations' logic.

    /**
     * Launches the ripple animation for the [target] starting from the tap point with [tapX] and [tapY].
     * */
    private fun launchRippleAnimation(target: Element, tapX: Int, tapY: Int) {

        // Preparing sizes and positions.
        val diameter   = max(target.clientWidth, target.clientHeight)
        val radius     = diameter / 2
        val targetRect = target.getBoundingClientRect()

        // Just a reusable variable.
        val animationClassName = rippleAnimationElement(contextProvider.invoke()).name

        // Cleaning up the previous animation elements.
        target.parentElement
            ?.getElementsByClassName(animationClassName)
            ?.run {
                for (i in 0 until length) {
                    this[i]?.remove()
                }
            }

        // Creating and appending the animation element.
        document.createElement("span").apply {
            setStyle {
                width  = diameter.px
                height = diameter.px
                left   = (tapX - targetRect.left - radius).px
                top    = (tapY - targetRect.top  - radius).px
            }
            classList.add(animationClassName)
            target.parentElement?.prepend(this)
        }

    }

    /** The dynamic [Context] source - must be set. */
    private var contextProvider: () -> Context = {
        throw IllegalStateException("Make sure to perform the initialization before any usage")
    }

    /** [MouseEventHandler] to launch the animation - for desktop (mouse-compatible) devices. */
    private val tapHighlightingMouseEventHandler: MouseEventHandler<*> = {
        if (it.isLeftButton) {
            launchRippleAnimation(it.currentTarget, it.clientX.toInt(), it.clientY.toInt())
        }
    }

    /** [TouchEventHandler] to launch the animation - for touch devices. */
    private val tapHighlightingTouchEventHandler: TouchEventHandler<*> = { event ->
        event.touches[0]?.let { launchRippleAnimation(event.currentTarget, it.clientX, it.clientY) }
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
        backgroundColor = Theme.palette.selectionFocused1(it)
        pointerEvents = PointerEvents.none // to avoid intercepting taps
    }

}
