package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.kodein.modules

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides various coroutines-related dependencies. */
@OptIn(DelicateCoroutinesApi::class)
internal val coroutinesModule inline get() = DI.Module("Coroutines") {
    bindSingleton<CoroutineScope> { GlobalScope }
}
