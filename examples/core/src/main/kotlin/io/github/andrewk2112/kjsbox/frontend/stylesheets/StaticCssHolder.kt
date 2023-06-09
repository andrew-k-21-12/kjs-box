package io.github.andrewk2112.kjsbox.frontend.stylesheets

import kotlinx.css.CssBuilder
import kotlinx.css.RuleSet
import styled.CssHolder
import styled.GlobalStyles
import kotlin.reflect.KProperty

/**
 * Copy-paste of the [CssHolder] configured to work with the [DynamicStyleSheet] instead.
 */
class StaticCssHolder(private val sheet: DynamicStyleSheet, private vararg val ruleSets: RuleSet) {

    operator fun provideDelegate(thisRef: Any?, providingProperty: KProperty<*>): Lazy<NamedRuleSet> {
        val className = sheet.getClassName(providingProperty)
        classNamesToInject[className] = true
        return lazy {
            // In contrary to the default implementation, the rules itself are not called directly every time:
            // for cases when the rules are read by the name, it should be guaranteed that the stylesheets are imported.
            sheet.scheduleImports()
            if (sheet.isStatic) {
                scheduleToInject(className)
            }
            NamedRuleSet(className) {
                if (sheet.isStatic) {
                    +className
                }
                if (!sheet.isStatic || !allowClasses || isHolder) {
                    styleName.add(sheet.getClassName(providingProperty))
                    ruleSets.forEach { it() }
                }
            }
        }
    }

    private fun scheduleToInject(className: String) {
        if (classNamesToInject[className] == true) {
            GlobalStyles.scheduleToInject(".$className", css)
            if (!areScheduledInjected) {
                areScheduledInjected = true
                GlobalStyles.injectScheduled()
            }
        }
    }

    /** Required to invoke the injection for scheduled styles only once per holder. */
    private var areScheduledInjected = false

    private val css by lazy {
        CssBuilder(allowClasses = false).apply {
            this@StaticCssHolder.ruleSets.map { it() }
        }
    }

    private val classNamesToInject = mutableMapOf<ClassName, Boolean>()

}

private typealias ClassName = String

private fun DynamicStyleSheet.getClassName(property: KProperty<*>): String {
    return "$name-${property.name}"
}
