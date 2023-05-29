package io.github.andrewk2112.wrappers.templates

import io.github.andrewk2112.extensions.indented
import org.intellij.lang.annotations.Language

/**
 * Basic templates to generate the code for font wrappers.
 */
internal class FontWrapperTemplates {

    // Utility.

    /**
     * Holds only those properties required to generate the code for a single font style wrapper.
     */
    internal class FontVariant(
        internal val variantPropertyName: String,
        internal val fontFamily: String,
        internal val variantName: String,
        internal val referencePropertyName: String,
        internal val fontFormat: String,
        internal val fontWeight: String,
        internal val fontStyle: String,
        internal val fontFamilyWithFallbacks: String,
        internal val relativeFontPath: String,
    )



    // API.

    /**
     * Inflates the code of a single source file
     * containing multiple font style properties within one Kotlin object.
     */
    @Language("kotlin")
    internal fun inflateFontStylesObject(
        packageName: String,
        objectName: String,
        variants: List<FontVariant>
    ): String = """
package $packageName

import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.*
import styled.injectGlobal

object $objectName : DynamicStyleSheet() {

${"    " indented variants.joinToString("\n\n") { it.inflateStyle() }}

}

${variants.joinToString("\n\n") { it.inflateReference() }}

    """.trimIndent()



    // Private.

    /**
     * Inflate the code of a single font style property.
     */
    @Language("kotlin")
    private fun FontVariant.inflateStyle(): String = """
val $variantPropertyName: NamedRuleSet by css {

    injectGlobal(
        CssBuilder().apply {
            fontFace {
                fontFamily = "$fontFamily"
                src = "local($fontFamily-$variantName), " +
                      "url(${'$'}$referencePropertyName) format(\"$fontFormat\")"
                fontWeight = $fontWeight
                fontStyle  = $fontStyle
            }
        }
    )

    fontFamily = "$fontFamilyWithFallbacks"
    fontWeight = $fontWeight
    fontStyle  = $fontStyle

}
    """.trimIndent()

    /**
     * Inflates the code of a single property referencing the original font file.
     */
    @Language("kotlin")
    private fun FontVariant.inflateReference(): String = """
@JsModule("./$relativeFontPath")
@JsNonModule
private external val $referencePropertyName: dynamic
    """.trimIndent()

}
