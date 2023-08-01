package io.github.andrewk2112.kjsbox.frontend.stylesheets

import kotlinx.css.CssBuilder

/**
 * Provides [NamedRuleSet]s with caching according to provided arguments.
 *
 * @param staticCssSuffix A suffix to be included into all names of dynamic CSS classes.
 * @param sheet           A reference to the [DynamicStyleSheet] containing and caching all related styles.
 * @param builder         A builder describing how to prepare CSS rules according to the current argument.
 */
class DynamicCssProvider<T : Any> internal constructor(
    val staticCssSuffix: String,
    private val sheet: DynamicStyleSheet,
    private val builder: CssBuilder.(T) -> Unit
) {

    operator fun invoke(argument: T): NamedRuleSet = sheet.prepareCachedRuleSet(staticCssSuffix, builder, argument)

}
