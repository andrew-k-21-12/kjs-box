plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers)
}

kotlin.sourceSets {
    val jsMain by getting {
        dependencies {
            implementation(kotlinLibs.kjsbox.frontend.designtokens)
            implementation(projects.dependencyInjection)
            implementation(projects.designTokens)
        }
    }
}

lazyModule {
    exportedComponent.set("io.github.andrewk2112.kjsbox.frontend.example.exercises.components.exercisesList")
}
