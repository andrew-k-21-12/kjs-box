package io.github.andrewk2112

import io.github.andrewk2112.components.exercisesList
import io.github.andrewk2112.containers.materialDesignScaffold
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.hooks.useStateGetterOnce
import io.github.andrewk2112.redux.reducers.ContextReducer
import io.github.andrewk2112.routes.MaterialDesignRoute
import org.kodein.di.direct
import org.kodein.di.instance
import react.*
import react.router.Route
import react.router.Routes

/** A placeholder to be shown while the application itself is loading. */
val appLoadingPlaceholder = fc<Props> {
    +"⌛ Loading / Загрузка"
}

/** The React application's entry point component: all basic React configurations and its rendering start here. */
val app = fc<Props> {

    // Starting to monitor a screen size to update the context dynamically.
    useStateGetterOnce { di.direct.instance<ContextReducer>() }.useScreenSizeMonitor()

    // All pages of the app: the root (fallback) one and the first example template.
    Routes {
        Route {
            attrs {
                path = "*"
                element = exercisesList.create()
            }
        }
        Route {
            attrs {
                path = MaterialDesignRoute.path
                element = materialDesignScaffold.create()
            }
        }
    }

}
