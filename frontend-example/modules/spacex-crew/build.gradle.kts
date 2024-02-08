plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers)
    alias(kotlinLibs.plugins.kotlin.serialization)
}

kotlin.sourceSets {
    val jsMain by getting {
        dependencies {
            implementation(kotlinLibs.kjsbox.frontend.core)
            implementation(kotlinLibs.kotlinx.coroutines.js)
            implementation(kotlinLibs.kotlinx.serialization)
            implementation(kotlinLibs.my.utility.common)
            implementation(kotlinLibs.my.utility.coroutines.react)
            implementation(kotlinLibs.my.utility.js)
            implementation(kotlinLibs.my.utility.react)
            implementation(projects.dependencyInjectionUtility)
        }
    }
}

lazyModule {
    exportedComponent.set(
        "io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.components.root"
    )
}
