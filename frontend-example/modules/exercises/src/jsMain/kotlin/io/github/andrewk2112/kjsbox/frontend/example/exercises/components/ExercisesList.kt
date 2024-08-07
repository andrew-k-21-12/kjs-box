package io.github.andrewk2112.kjsbox.frontend.example.exercises.components

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.exercises.SourceSansProFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.TranslationLocalizationKeys.MATERIAL_DESIGN_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.TranslationLocalizationKeys.NAMESPACE
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.TranslationLocalizationKeys.SPACE_X_CREW_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.TranslationLocalizationKeys.TO_DO_LIST_KEY
import io.github.andrewk2112.kjsbox.frontend.example.routes.MaterialDesignRoute
import io.github.andrewk2112.kjsbox.frontend.example.routes.SpaceXCrewRoute
import io.github.andrewk2112.kjsbox.frontend.example.routes.ToDoListRoute
import io.github.andrewk2112.kjsbox.frontend.route.absolutePath
import io.github.andrewk2112.utility.react.components.FC
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import kotlinx.css.*
import react.ChildrenBuilder
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul



// Components.

val ExercisesList by FC {

    val context     = useDesignTokensContext()
    val localizator = useLocalizator(NAMESPACE)
    val component   = useRootComponent()
    val styles      = useMemoWithReferenceCount(component) { ExercisesListStyles(component.getDesignTokens()) }

    +div(clazz = styles.container.name) {

        // The list of available exercises.
        ul {
            LinkItem(context, styles, localizator(MATERIAL_DESIGN_KEY), MaterialDesignRoute.absolutePath())
            LinkItem(context, styles, localizator(SPACE_X_CREW_KEY),    SpaceXCrewRoute.absolutePath())
            LinkItem(context, styles, localizator(TO_DO_LIST_KEY),      ToDoListRoute.absolutePath())
        }

    }

}

@Suppress("FunctionName")
private fun ChildrenBuilder.LinkItem(
    context: Context,
    styles: ExercisesListStyles,
    label: String,
    destinationEndpoint: String
) =
    +li(clazz = styles.listItem.name) { ExerciseLink(context, label, destinationEndpoint) }



// Styles.

private class ExercisesListStyles(private val designTokens: DesignTokens) : DynamicStyleSheet(designTokens::class) {

    val container: NamedRuleSet by css {
        +SourceSansProFontStyles.regular.rules
        fontSize = 100.pct
    }

    val listItem: NamedRuleSet by css {
        val margin  = designTokens.reference.spacing.absolute5
        marginTop   = margin
        marginLeft  = margin
        marginRight = margin
        lastChild {
            marginBottom = margin
        }
    }

}
