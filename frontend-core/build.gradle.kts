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

    // Kotlin Wrappers BOM to ensure consistency between the modules and version compatibility.
    implementation(enforcedPlatform(kotlinLibs.kotlin.wrappers.bom))

    // React and fellows.
    implementation(kotlinLibs.kotlin.wrappers.react)
    implementation(kotlinLibs.kotlin.wrappers.react.redux)      // to use global state in React
    implementation(kotlinLibs.kotlin.wrappers.react.router.dom) // to process routes

    // Other wrappers.
    implementation(kotlinLibs.kotlin.wrappers.styled.next) // to declare and reuse styles directly in code
    implementation(kotlinLibs.kotlin.wrappers.js)          // wrappers for JS entities

    // Dependency injection.
    implementation(kotlinLibs.kodein.di)

    // All required JS libraries.
    jsLibs.bundles.kjsbox.frontend.core.get().forEach {
        implementation(npm(it))
    }

}
