package io.github.andrewk2112.jsmodules.svg

import react.*

/**
 * Syntax sugar to inline SVG files into HTML contents gracefully.
 * */
external interface SvgFile {

    /**
     * Returns SVG file's contents as a React component.
     * */
    @JsName("default")
    val component: ElementType<Props>

}

/**
 * Prepares a [ReactNode] ready to be inserted into the DOM tree.
 * */
operator fun SvgFile.invoke(): ReactNode = component.create()
