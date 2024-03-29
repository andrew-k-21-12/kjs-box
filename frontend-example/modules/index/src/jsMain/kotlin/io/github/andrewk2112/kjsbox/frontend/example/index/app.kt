package io.github.andrewk2112.kjsbox.frontend.example.index

import frontendExampleExercisesEntryPoint
import frontendExampleMaterialDesignEntryPoint
import frontendExampleSpacexCrewEntryPoint
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.designTokensContextProvider
import io.github.andrewk2112.kjsbox.frontend.example.routes.MaterialDesignRoute
import io.github.andrewk2112.kjsbox.frontend.example.routes.SpaceXCrewRoute
import react.*
import react.router.*
import react.router.dom.BrowserRouter

/** The React application's entry point component: all basic React configurations and its rendering start here. */
val app = FC {
    BrowserRouter { // enabling routing features,
        Suspense {  // configuring the app with its loading placeholder
            fallback = appLoadingPlaceholder.create()
            designTokensContextProvider {
                routes()
            }
        }
    }
}

/** A placeholder to be shown while the application itself is loading. */
private val appLoadingPlaceholder = FC {
    +"⌛ Loading / Загрузка"
}

/** All the actual contents available in the app bound to the corresponding routes. */
private val routes = FC {

    // All pages of the app: the root (serves as a fallback also) one,
    // a couple of example pages and the fallback configuration.
    // React processes its routes in some special way which differs from the web's canonical one:
    // in the context of the routes below it is only important
    // that trailing slashes path variants are included automatically
    // (i.e. declaring "/material-design" will also include "/material-design/")
    // and for nested routes relative paths should be used to join a segment to its parent,
    // or we need to qualify a full absolute (starting with a slash) path for this nested route.
    // More details here: https://reactrouter.com/docs/en/v6/upgrading/v5#note-on-link-to-values.
    Routes {
        PathRoute {
            path    = "/"
            element = frontendExampleExercisesEntryPoint.create()
        }
        PathRoute {
            path    = MaterialDesignRoute.path
            element = frontendExampleMaterialDesignEntryPoint.create()
        }
        PathRoute {
            path    = SpaceXCrewRoute.path
            element = frontendExampleSpacexCrewEntryPoint.create()
        }
        PathRoute {
            path    = "*"
            element = FC {
                val navigate = useNavigate()
                useEffect {
                    navigate("/")
                }
            }.create()
        }
    }

}
