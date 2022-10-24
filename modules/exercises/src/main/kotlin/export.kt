import io.github.andrewk2112.exercises.components.exercisesList
import react.FC
import react.Props

/**
 * For lazy loading of components they should be exported as default.
 * Export will not work with any visibility modifier excepting public.
 * We can not name the exported variable as "default" as it leads to the clash of public names.
 * This boilerplate is needed to remove the package from the component being exported
 * without harming its internal imports.
 * It seems reasonable to create some automation for such dirty exports.
 * */
@JsExport
@OptIn(ExperimentalJsExport::class)
@JsName("default")
@Suppress("NON_CONSUMABLE_EXPORTED_IDENTIFIER") // to avoid pointless warnings in the console which are not true
val exercisesList: FC<Props> = exercisesList
