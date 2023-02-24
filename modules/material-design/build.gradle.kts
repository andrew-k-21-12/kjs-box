import io.github.andrewk2112.gradle.plugins.ResourceWrappersGenerationPlugin

plugins {
    kotlin("js")
}
apply<ResourceWrappersGenerationPlugin>()

kotlin {
    js(IR).browser()
}

dependencies {
    implementation(project(":core"))
}
