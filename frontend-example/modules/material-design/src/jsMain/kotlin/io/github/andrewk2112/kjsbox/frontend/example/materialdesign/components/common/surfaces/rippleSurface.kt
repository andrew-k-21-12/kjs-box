package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.surfaces

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.extensions.isLeftButton
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.extensions.setStyle
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.materialDesignTokens
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.scale
import kotlinx.css.properties.transform
import react.*
import react.dom.DOMProps
import react.dom.events.MouseEvent
import react.dom.events.MouseEventHandler
import react.dom.events.TouchEvent
import react.dom.events.TouchEventHandler
import react.dom.html.ReactHTML.div
import styled.animation
import web.dom.Element
import web.html.HTMLDivElement
import kotlin.math.max



// Public.

val rippleSurface = FC<DOMProps> { props ->

    val eventHandlers = RippleSurfaceEventHandlers(useAppContext())

    +div(RippleSurfaceStyles.rippleAnimationContainer.name, props.className.toString()) {
        onTouchStart = eventHandlers::touchEventHandler
        onMouseDown  = eventHandlers::mouseEventHandler
        +props.children
    }

}



// Private.

/**
 * This value class is a naive attempt to avoid allocations of handlers required to launch the ripple animation
 * each time the [context] updates, but in fact there are no guarantees that value classes won't be allocated.
 */
private value class RippleSurfaceEventHandlers(private val context: Context) {

    /**
     * [TouchEventHandler] to launch the ripple animation.
     */
    fun touchEventHandler(event: TouchEvent<HTMLDivElement>) {
        event.touches[0].apply {
            launchRippleAnimation(event.currentTarget, clientX, clientY)
        }
    }

    /**
     * [MouseEventHandler] to launch the ripple animation.
     */
    fun mouseEventHandler(event: MouseEvent<HTMLDivElement, *>) {
        if (event.isLeftButton) {
            launchRippleAnimation(event.currentTarget, event.clientX, event.clientY)
        }
    }

    /**
     * Launches the ripple animation for a [target] starting from a tap point with [tapX] and [tapY].
     */
    private fun launchRippleAnimation(target: Element, tapX: Double, tapY: Double) {

        // Preparing sizes and positions.
        val diameter   = max(target.clientWidth, target.clientHeight)
        val radius     = diameter / 2
        val targetRect = target.getBoundingClientRect()

        // Cleaning up previous animation elements.
        target
            .querySelectorAll("[class*=${RippleSurfaceStyles.rippleAnimationElement.staticCssSuffix}]")
            .forEach { target.removeChild(it) }

        // Creating and appending the animation element.
        document.createElement("span").apply {
            setStyle {
                width  = diameter.px
                height = diameter.px
                left   = (tapX - targetRect.left - radius).px
                top    = (tapY - targetRect.top  - radius).px
            }
            classList.add(RippleSurfaceStyles.rippleAnimationElement(context).name)
            target.prepend(this)
        }

    }

}

private object RippleSurfaceStyles : DynamicStyleSheet() {

    val rippleAnimationContainer by css {
        overflow = Overflow.hidden   // these two configs are needed
        position = Position.relative // to prevent the ripple from getting outside the bounds
    }

    val rippleAnimationElement: DynamicCssProvider<Context> by dynamicCss {

        position = Position.absolute
        borderRadius = 50.pct // to make it absolutely round
        backgroundColor = materialDesignTokens.system.palette.selection1Active(it)
        pointerEvents = PointerEvents.none // to avoid intercepting taps

        transform { scale(0) }
        animation(
            duration = materialDesignTokens.reference.time.ms600,
            timing   = materialDesignTokens.reference.timing.linear,
            builder  = {
                to {
                    transform { scale(4) }
                    opacity = materialDesignTokens.reference.opacities.transparent
                }
            }
        )

    }

}