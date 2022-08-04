package io.github.andrewk2112.hooks

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.redux.reducers.ContextReducer
import org.kodein.di.direct
import org.kodein.di.instance
import react.useState

/**
 * A getter hook to read the app's [Context] simpler.
 * */
fun useAppContext(): Context = useState { di.direct.instance<ContextReducer>() }.component1().useSelector()
