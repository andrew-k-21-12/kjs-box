package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.Context
import kotlinx.css.Color
import kotlinx.css.LinearDimension

/**
 * A protocol to describe dynamic themed sizes:
 * returns some specific size ([LinearDimension]) according to the provided [Context].
 * */
typealias ThemedSize = (Context) -> LinearDimension

/**
 * A protocol to describe dynamic themed colors:
 * returns some specific [Color] according to the provided [Context].
 * */
typealias ThemedColor = (Context) -> Color
