import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.extensions.npm

plugins {
    alias(kotlinLibs.plugins.kotlin.js)
}

buildscript {
    dependencies {
        classpath(kotlinLibs.kjsbox.frontend.commongradleextensions)
    }
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0-SNAPSHOT"

kotlin {
    js().browser()
}

dependencies {

    // It's possible to get rid of `api` and use `implementation` when there will be more fine-grained dependencies.

    // Kotlin Wrappers BOM to ensure consistency between the modules and version compatibility.
    api(platform(kotlinLibs.kotlin.wrappers.bom))

    // React and fellows.
    api(kotlinLibs.kotlin.wrappers.react)
    api(kotlinLibs.kotlin.wrappers.react.redux)      // to use global state in React
    api(kotlinLibs.kotlin.wrappers.react.router.dom) // to process routes

    // Other wrappers.
    api(kotlinLibs.kotlin.wrappers.styled.next)   // to declare and reuse styles directly in code
    implementation(kotlinLibs.kotlin.wrappers.js) // wrappers for JS entities

    // Dependency injection.
    api(kotlinLibs.kodein.di)

    // All required JS libraries.
    jsLibs.bundles.kjsbox.frontend.core.get().forEach {
        implementation(npm(it))
    }

}
