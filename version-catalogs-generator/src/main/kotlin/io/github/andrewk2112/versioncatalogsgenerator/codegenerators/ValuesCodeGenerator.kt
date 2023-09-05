package io.github.andrewk2112.versioncatalogsgenerator.codegenerators

import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog

/**
 * Generalization to write code of different value groups presented inside a [ParsedVersionCatalog]
 * (for example - versions, libraries, plugins and so on).
 */
internal interface ValuesCodeGenerator {

    /**
     * For better encapsulation of output sources they are divided into two parts:
     * a public interface and a public property implementing this interface and providing actual catalog values.
     */
    class GeneratedCode(
        internal val interfaceCode: String,
        internal val implementationPropertyCode: String,
    )

    /**
     * See the [ValuesCodeGenerator] interface's description.
     *
     * @return `null` - if no values are present in the catalog's group of interest.
     */
    fun generateValuesCode(catalog: ParsedVersionCatalog): GeneratedCode?

}
