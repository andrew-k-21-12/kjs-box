package io.github.andrewk2112.designtokens.stylesheets

import kotlinx.css.CssBuilder
import kotlinx.css.RuleSet
import styled.CssHolder
import styled.GlobalStyles

/**
 * A variation of the [CssHolder] simplified to inject and provide styles without delegation features.
 * */
internal class DynamicCssHolder(
    private val sheet: DynamicStyleSheet,
    cssSuffix: String,
    private vararg val ruleSets: RuleSet
) {

    // Internal.

    /**
     * Contains the initialization part of the delegate from [CssHolder].
     * */
    internal fun markToInject() {
        classNamesToInject[className] = true
    }

    /**
     * Contains property providing part from the [CssHolder].
     * */
    internal fun provideRuleSet(): RuleSet = {
        sheet.scheduleImports()
        if (sheet.isStatic) {
            scheduleToInject(className)
            +className
        }
        if (!sheet.isStatic || !allowClasses || isHolder) {
            styleName.add(className)
            ruleSets.forEach { it() }
        }
    }



    // Private.

    private fun scheduleToInject(className: String) {
        if (classNamesToInject[className] == true) {
            GlobalStyles.scheduleToInject(".$className", css)
        }
    }

    private val css: CssBuilder by lazy {
        CssBuilder(allowClasses = false).apply {
            ruleSets.map { it() }
        }
    }

    private val classNamesToInject = mutableMapOf<String, Boolean>()

    private val className = "${sheet.name}-$cssSuffix"

}
