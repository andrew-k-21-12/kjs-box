package io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet

import io.github.andrewk2112.utility.string.formats.cases.KebabCase
import io.github.andrewk2112.utility.string.formats.cases.LowerCamelCase
import io.github.andrewk2112.utility.string.formats.cases.SnakeCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import kotlinx.css.CssBuilder
import kotlinx.css.RuleSet
import styled.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Mostly an equivalent of [StyleSheet] but allows providing dynamic styles in addition to static ones.
 */
open class DynamicStyleSheet {

    // Public.

    /**
     * This constructor allows to provide an end [name] for a style sheet:
     * if it's `null` then the class name is used.
     */
    constructor(name: String? = null, isStatic: Boolean = defaultIsStatic, imports: List<Import> = defaultImports) {
        this.name     = name ?: this::class.createStyleName()
        this.isStatic = isStatic
        this.imports  = imports
    }

    /**
     * This constructor allows to provide all [additionalSubNames] to contribute the final style sheet name.
     * If no [additionalSubNames] are provided, only this class's name is used.
     */
    constructor(
        vararg additionalSubNames: KClass<*>,
        isStatic: Boolean      = defaultIsStatic,
        imports:  List<Import> = defaultImports,
    ) {
        name = if (additionalSubNames.isEmpty()) {
            this::class.createStyleName()
        } else {
            arrayOf(this::class, *additionalSubNames)
                .joinToString("-") { it.createStyleName() }
        }
        this.isStatic = isStatic
        this.imports  = imports
    }



    // Protected.

    /**
     * Helps to declare properties with dynamic styles according to provided arguments.
     *
     * @param builder Describes how to prepare a style according to the current argument.
     *
     * @return An instance of the [DynamicCssDelegate] allowing the target property to invoke required styles.
     */
    protected fun <T : Any> dynamicCss(builder: CssBuilder.(T) -> Unit) = DynamicCssDelegate(this, builder)

    /**
     * Provides a delegate for regular static style holders.
     */
    protected fun css(vararg parents: RuleSet, builder: RuleSet) =
        StaticCssHolder(this, *parents, builder)
            .also { addCssHolder(it) }



    // Internal.

    /**
     * Creates a new or uses an already cached [RuleSet] corresponding to the provided [argument].
     *
     * @param staticCssSuffix Some unique static (keeping even if the [argument] changes) name for a style:
     *                        usually a property name.
     * @param builder Describes how to prepare styles for the particular [argument].
     * @param argument Some kind of seed and identifier to prepare a [RuleSet].
     *
     * @return A prepared [NamedRuleSet] ready to be used.
     */
    internal fun <T : Any> prepareCachedRuleSet(
        staticCssSuffix: String,
        builder: CssBuilder.(T) -> Unit,
        argument: T
    ): NamedRuleSet {
        val fullCssSuffix = "$staticCssSuffix-${argument.extractCssSuffix()}"
        var hasGotNewStyle = false
        val holder = dynamicHolders.getOrPut(fullCssSuffix) {
            hasGotNewStyle = true
            DynamicCssHolder(this, fullCssSuffix, { builder.invoke(this, argument) })
                .also { it.markToInject() }
        }
        return holder.provideRuleSet().also {
            // After the call above, a new style is getting marked to be injected,
            // so it's reasonable to inject it once right after its creation.
            if (hasGotNewStyle) {
                GlobalStyles.injectScheduled()
            }
        }
    }

    internal fun scheduleImports() {
        if (imports.isNotEmpty()) {
            GlobalStyles.scheduleImports(imports)
            imports = emptyList()
        }
    }

    /** Root name to be applied to all styles declared by the instance of this class. */
    internal val name: String

    /** See the explanations for [StyleSheet.isStatic]. */
    internal val isStatic: Boolean



    // Private.

    /**
     * Provides ground truth values for default arguments.
     */
    private companion object {
        private val defaultIsStatic inline get() = true
        private val defaultImports  inline get() = emptyList<Import>()
    }

    /**
     * Generates a style sheet name or some of its parts.
     *
     * Maybe one day there will be needed some extended logic to avoid intersections in generated names.
     */
    private fun KClass<*>.createStyleName(): String =
        simpleName
            ?: js.name.replace("$", "").replace(".", "").also {
                console.warn("No class name is available to generate a style sheet name: $it")
            }

    private fun addCssHolder(holder: StaticCssHolder) {
        staticHolders.add(holder)
    }

    /**
     * Extracts a CSS suffix according to the argument type.
     *
     * @throws IllegalArgumentException When some unsupported argument type is encountered.
     */
    private fun Any.extractCssSuffix(): String = when (this) {
        is Boolean      -> toString()
        is Number       -> toString().revampCssSuffix()
        is String       -> revampCssSuffix()
        is HasCssSuffix -> cssSuffix.revampCssSuffix()
        is Enum<*>      -> name.revampCssSuffix().changeFormat(KebabCase, SnakeCase, to = LowerCamelCase)
        is KProperty<*> -> name.revampCssSuffix()
        else            -> throw IllegalArgumentException("The provided type is not supported for dynamic CSS")
    }

    /**
     * Revamps the receiver [String] to work correctly as a part of a CSS class name.
     */
    private fun String.revampCssSuffix() = replace(" ", "-").replace(".", "-") // removing CSS.escape(...) for now
                                                                               // as it converts numbers to Unicode

    /** See the explanations for [StyleSheet.imports]. */
    private var imports: List<Import>

    /** Keeps all holders providing dynamic styles - the holders are cached by their CSS suffixes. */
    private val dynamicHolders = mutableMapOf<String, DynamicCssHolder>()

    /** Keeps all holders providing static styles. */
    private val staticHolders = mutableListOf<StaticCssHolder>()

}
