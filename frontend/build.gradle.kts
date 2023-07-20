plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("kjs-box-frontend-main") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-main"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.gradle.plugins.MainModulePlugin"
        }
        create("kjs-box-frontend-entry-point") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-entry-point"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.gradle.plugins.EntryPointModulePlugin"
        }
        create("kjs-box-frontend-lazy-module") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-lazy-module"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.gradle.plugins.LazyModulePlugin"
        }
        create("kjs-box-frontend-resource-wrappers") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-resource-wrappers"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.gradle.plugins.ResourceWrappersGenerationPlugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.kotlin.gradleplugin) // to create plugins with Kotlin features
    implementation(kotlinLibs.commons.io) // to simplify some file operations a bit
}
