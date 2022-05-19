package io.github.andrewk2112

import io.github.andrewk2112.ui.containers.exercises.exercisesList
import io.github.andrewk2112.ui.containers.md.materialDesign
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.hooks.useStateGetterOnce
import io.github.andrewk2112.redux.reducers.ContextReducer
import io.github.andrewk2112.routes.MaterialDesignRoute
import org.kodein.di.direct
import org.kodein.di.instance
import react.*
import react.router.Navigate
import react.router.Route
import react.router.Routes

/** A placeholder to be shown while the application itself is loading. */
val appLoadingPlaceholder = FC<Props> {
    +"⌛ Loading / Загрузка"
}

/** The React application's entry point component: all basic React configurations and its rendering start here. */
val app = FC<Props> {

    // Starting to monitor a screen size to update the context dynamically.
    useStateGetterOnce { di.direct.instance<ContextReducer>() }.useScreenSizeMonitor()

    // All pages of the app: the root (serves as a fallback also) one,
    // the first example page and the fallback configuration.
    // React processes its routes in some special way which differs from the web's canonical one:
    // in the context of the routes below it is only important
    // that trailing slashes path variants are included automatically
    // (i.e. declaring "/material-design" will also include "/material-design/")
    // and for nested routes relative paths should be used to join a segment to its parent,
    // or we need to qualify a full absolute (starting with a slash) path for this nested route.
    // More details here: https://reactrouter.com/docs/en/v6/upgrading/v5#note-on-link-to-values.
    Routes {
        Route {
            path = "/"
            element = exercisesList.create()
        }
        Route {
            path = MaterialDesignRoute.path
            element = materialDesign.create()
        }
        Route {
            path = "*"
            element = FC<Props> {
                Navigate { to = "/" }
            }.create()
        }
    }

}
