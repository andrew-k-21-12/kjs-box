package io.github.andrewk2112.designtokens.stylesheets

import kotlinx.css.CssBuilder
import kotlinx.css.RuleSet
import styled.GlobalStyles
import styled.Import
import styled.StyleSheet

/**
 * Mostly an equivalent of [StyleSheet] but allows providing dynamic styles in addition to static ones.
 * */
open class DynamicStyleSheet(
    name: String? = null,
    val isStatic: Boolean = true,
    private var imports: List<Import> = emptyList()
) {

    // Public.

    /**
     * Helps to declare properties with dynamic styles according to provided arguments.
     *
     * @param builder Describes how to prepare a style according to the current argument.
     *
     * @return An instance of [DynamicCssDelegate] allowing the target variable to invoke required styles.
     * */
    fun <T : HasCssSuffix> dynamicCss(builder: CssBuilder.(T) -> Unit) = DynamicCssDelegate(this, builder)

    /**
     * Provides a delegate for regular static style holders.
     * */
    fun css(vararg parents: RuleSet, builder: RuleSet) =
        StaticCssHolder(this, *parents, builder)
            .also { addCssHolder(it) }

    /** A root name to be applied to all styles declared by the instance of this class. */
    val name: String = name // maybe one day some extended logic will be needed to avoid intersections in names
        ?: this::class.simpleName
        ?: this::class.js.name.replace("$", "").replace(".", "").also {
            console.warn("Style sheet with no name specified: $it")
        }



    // Internal.

    /**
     * Creates a new or uses already cached [RuleSet] corresponding to the provided [argument].
     *
     * @param staticCssSuffix Some unique static (keeping even if the [argument] changes) name for a style:
     *                        usually a property name.
     * @param builder Describes how to prepare styles for the particular [argument].
     * @param argument Some kind of seed and identifier to prepare a [RuleSet].
     *
     * @return Prepared [NamedRuleSet] ready to be used.
     * */
    internal fun <T : HasCssSuffix> prepareCachedRuleSet(
        staticCssSuffix: String,
        builder: CssBuilder.(T) -> Unit,
        argument: T
    ): NamedRuleSet {
        val fullCssSuffix = "$staticCssSuffix-${argument.cssSuffix}"
        val holder = dynamicHolders.getOrPut(fullCssSuffix) {
            DynamicCssHolder(this, fullCssSuffix, { builder.invoke(this, argument) })
                .also { it.markToInject() }
        }
        return holder.provideRuleSet()
    }

    internal fun scheduleImports() {
        if (imports.isNotEmpty()) {
            GlobalStyles.scheduleImports(imports)
            imports = emptyList()
        }
    }



    // Private.

    private fun addCssHolder(holder: StaticCssHolder) {
        staticHolders.add(holder)
    }

    /** Keeps all holders providing dynamic styles - the holders are cached by their CSS suffixes. */
    private val dynamicHolders = mutableMapOf<String, DynamicCssHolder>()

    /** Keeps all holders providing static styles. */
    private val staticHolders = mutableListOf<StaticCssHolder>()

}