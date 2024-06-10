package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.templates

import io.github.andrewk2112.utility.string.formats.cases.CamelCase
import io.github.andrewk2112.utility.string.formats.cases.LowerCamelCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import org.intellij.lang.annotations.Language

/**
 * Basic template to generate the code for various icon wrappers.
 */
internal class IconWrapperTemplate {

    /**
     * Generates the code for a concrete icon wrapper.
     */
    @Language("kotlin")
    internal fun inflate(packageName: String, iconNameInCamelCase: String, relativeIconPath: String): String {
        val iconNameInLowerCamelCase = iconNameInCamelCase.changeFormat(CamelCase, LowerCamelCase)
        return """
package $packageName

import js.import.Module
import react.ElementType
import react.PropsWithClassName

val $iconNameInCamelCase: ElementType<PropsWithClassName> by ${iconNameInLowerCamelCase}Reference::default

@JsModule("./$relativeIconPath")
@JsNonModule
private external val ${iconNameInLowerCamelCase}Reference: Module<ElementType<PropsWithClassName>>

        """.trimIndent()
    }

}
