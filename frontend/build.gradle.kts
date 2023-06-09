plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        // Allows to expose all other plugins and DSL functions, prone to be changed in the future.
        create("kjs-box-frontend-main") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-main"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.gradle.plugins.MainPlugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.kotlin.gradleplugin) // to create plugins with Kotlin features
    implementation(kotlinLibs.commons.io) // to simplify some file operations a bit
}
