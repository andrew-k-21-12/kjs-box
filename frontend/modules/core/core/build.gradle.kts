plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0-SNAPSHOT"

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {

                // It's possible to get rid of `api` and use `implementation`
                // when there will be more fine-grained dependencies.

                // Kotlin Wrappers BOM to ensure consistency between the modules and version compatibility.
                api(platform(kotlinLibs.kotlin.wrappers.bom.get()))

                // React and fellows.
                api(kotlinLibs.kotlin.wrappers.react)
                api(kotlinLibs.kotlin.wrappers.react.router.dom) // to process routes

                // Other wrappers.
                api(kotlinLibs.kotlin.wrappers.styled.next)   // to declare and reuse styles directly in code
                implementation(kotlinLibs.kotlin.wrappers.js) // wrappers for JS entities

                // Dependency injection.
                api(kotlinLibs.kodein.di)

                // Various utility.
                implementation(kotlinLibs.kjsbox.frontend.dynamicstylesheet)
                implementation(kotlinLibs.my.utility.string)

            }
        }
    }
}
