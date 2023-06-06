import io.github.andrewk2112.gradle.plugins.ResourceWrappersGenerationPlugin

plugins {
    alias(kotlinLibs.plugins.kotlin.js)
}
apply<ResourceWrappersGenerationPlugin>()

kotlin {
    js(IR).browser()
}

dependencies {
    implementation(projects.core)
}
