package io.github.andrewk2112.versioncatalogsgenerator.extensions

import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator

/**
 * Creates an instance of [ValuesCodeGenerator.GeneratedCode]:
 * treats the first element as interfaces code, the second - as implementation code,
 * returns `null` if the element count is not equal to 2.
 */
internal fun Array<String>.toGeneratedCode(): ValuesCodeGenerator.GeneratedCode? {
    if (size != 2) return null
    return ValuesCodeGenerator.GeneratedCode(first(), last())
}
