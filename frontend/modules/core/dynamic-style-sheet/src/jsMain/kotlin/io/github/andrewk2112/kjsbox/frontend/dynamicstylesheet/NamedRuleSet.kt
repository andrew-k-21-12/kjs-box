package io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet

import kotlinx.css.RuleSet

/**
 * A wrapper for a [RuleSet] to make its usage possible both directly and by [name].
 */
class NamedRuleSet internal constructor(val name: String, val rules: RuleSet)
