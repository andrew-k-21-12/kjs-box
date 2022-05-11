package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import kotlinx.css.Color
import kotlinx.css.LinearDimension

/**
 * A protocol to describe dynamic themed font faces:
 * returns some specific font face according to the provided [Context].
 * */
typealias ThemedFontFace = (Context) -> NamedRuleSet

/**
 * A protocol to describe dynamic themed font sizes:
 * returns some specific font size according to the provided [Context].
 * */
typealias ThemedFontSize = (Context) -> LinearDimension

/**
 * A protocol to describe dynamic themed colors:
 * returns some specific [Color] according to the provided [Context].
 * */
typealias ThemedColor = (Context) -> Color
