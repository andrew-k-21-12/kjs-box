plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers)
}

lazyModule {
    exportedComponent.set("io.github.andrewk2112.kjsbox.frontend.example.exercises.components.exercisesList")
}
