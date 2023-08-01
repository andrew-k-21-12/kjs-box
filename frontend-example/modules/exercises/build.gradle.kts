plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-lazy-module")
    id("io.github.andrew-k-21-12.kjs-box.frontend-resource-wrappers")
}

lazyModule {
    exportedComponent.set("io.github.andrewk2112.kjsbox.frontend.example.exercises.components.exercisesList")
}
