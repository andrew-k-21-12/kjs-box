package io.github.andrewk2112.kjsbox.frontend.dsl.wrappers.templates

import org.intellij.lang.annotations.Language

/**
 * Basic template to generate the code for various icon wrappers.
 */
internal class IconWrapperTemplate {

    /**
     * Generates the code for a concrete icon wrapper.
     */
    @Language("kotlin")
    internal fun inflate(packageName: String, elementName: String, relativeIconPath: String): String = """
package $packageName

import js.import.Module
import react.ElementType
import react.PropsWithClassName

val $elementName: ElementType<PropsWithClassName> by ${elementName}Reference::default

@JsModule("./$relativeIconPath")
@JsNonModule
private external val ${elementName}Reference: Module<ElementType<PropsWithClassName>>

    """.trimIndent()

}
