package io.github.andrewk2112.designtokens.stylesheets

import kotlinx.css.CssBuilder
import kotlinx.css.RuleSet
import styled.CssHolder
import styled.GlobalStyles
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Copy-paste of the [CssHolder] configured to work with the [DynamicStyleSheet] instead.
 * */
class StaticCssHolder(private val sheet: DynamicStyleSheet, private vararg val ruleSets: RuleSet) {

    operator fun provideDelegate(thisRef: Any?, providingProperty: KProperty<*>): ReadOnlyProperty<Any?, RuleSet> {
        val className = sheet.getClassName(providingProperty)
        classNamesToInject[className] = true
        return ReadOnlyProperty { _, property ->
            {
                sheet.scheduleImports()
                if (sheet.isStatic) {
                    scheduleToInject(className)
                    +className
                }
                if (!sheet.isStatic || !allowClasses || isHolder) {
                    styleName.add(sheet.getClassName(property))
                    ruleSets.forEach { it() }
                }
            }
        }
    }

    private fun scheduleToInject(className: String) {
        if (classNamesToInject[className] == true) {
            GlobalStyles.scheduleToInject(".$className", css)
        }
    }

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
