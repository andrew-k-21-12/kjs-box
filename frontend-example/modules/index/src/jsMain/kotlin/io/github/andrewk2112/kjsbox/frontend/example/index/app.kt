package io.github.andrewk2112.kjsbox.frontend.example.index

import frontendExampleExercisesEntryPoint
import frontendExampleMaterialDesignEntryPoint
import io.github.andrewk2112.kjsbox.frontend.core.redux.StoreFactory
import io.github.andrewk2112.kjsbox.frontend.core.redux.reducers.ContextReducer
import io.github.andrewk2112.kjsbox.frontend.core.routes.MaterialDesignRoute
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.rootComponent
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.hooks.useInjected
import org.kodein.di.direct
import org.kodein.di.instance
import react.*
import react.redux.Provider
import react.router.*
import react.router.dom.BrowserRouter

/** The React application's entry point component: all basic React configurations and its rendering start here. */
val app = FC {
    Provider {
        store = rootComponent.direct.instance<StoreFactory>().create() // setting the global app state and its processing reducers,
        BrowserRouter {                                                // enabling routing features,
            Suspense {                                                 // configuring the app with its loading placeholder
                fallback = appLoadingPlaceholder.create()
                initializations()
                routes()
            }
        }
    }
}

/** A placeholder to be shown while the application itself is loading. */
private val appLoadingPlaceholder = FC {
    +"⌛ Loading / Загрузка"
}

/** All required initializations to be done before loading of any actual contents. */
private val initializations = FC {
    // Monitoring the screen size to update the context.
    rootComponent.useInjected<ContextReducer>().useScreenSizeMonitor()
}

/** All the actual contents available in the app bound to the corresponding routes. */
private val routes = FC {

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
        PathRoute {
            path = "/"
            element = frontendExampleExercisesEntryPoint.create()
        }
        PathRoute {
            path = MaterialDesignRoute.path
            element = frontendExampleMaterialDesignEntryPoint.create()
        }
        PathRoute {
            path = "*"
            element = FC {
                val navigate = useNavigate()
                useEffect {
                    navigate("/")
                }
            }.create()
        }
    }

}
