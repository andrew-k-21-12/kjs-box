package io.github.andrewk2112

import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.redux.StoreFactory
import io.github.andrewk2112.redux.reducers.ContextReducer
import io.github.andrewk2112.routes.MaterialDesignRoute
import kotlinx.js.Module
import kotlinx.js.import
import org.kodein.di.direct
import org.kodein.di.instance
import react.*
import react.redux.Provider
import react.router.Navigate
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter

// Public.

/** The React application's entry point component: all basic React configurations and its rendering start here. */
val app = FC<Props> {
    Provider {
        store = di.direct.instance<StoreFactory>().create() // setting the global app state and its processing reducers,
        BrowserRouter {                                     // enabling routing features,
            Suspense {                                      // configuring the app with its loading placeholder
                fallback = appLoadingPlaceholder.create()
                contents()
            }
        }
    }
}



// Private - components.

/** A placeholder to be shown while the application itself is loading. */
private val appLoadingPlaceholder = FC<Props> {
    +"⌛ Loading / Загрузка"
}

/** All the actual contents available in the app. */
private val contents = FC<Props> {

    // Starting to monitor a screen size to update the context dynamically.
    useState { di.direct.instance<ContextReducer>() }.component1().useScreenSizeMonitor()

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
            element = lazyExercisesList.create()
        }
        Route {
            path = MaterialDesignRoute.path
            element = lazyMaterialDesign.create()
        }
        Route {
            path = "*"
            element = FC<Props> {
                Navigate { to = "/" }
            }.create()
        }
    }

}

// Elements to be loaded lazily.
private val lazyExercisesList: ExoticComponent<Props> = lazy {
    import<Module<dynamic>>("./${Environment.projectName}-exercises")
        .then{ it.default }
}
private val lazyMaterialDesign: ExoticComponent<Props> = lazy {
    import<Module<dynamic>>("./${Environment.projectName}-material-design")
        .then{ it.default }
}
