import io.github.andrewk2112.extensions.rootTaskOfType
import io.github.andrewk2112.gradle.tasks.FontWrappersGenerationTask

plugins {
    kotlin("js")
}

kotlin {
    js(IR).browser()
    // Using the required generated font wrappers in the module's sources.
    sourceSets.main.get().kotlin.srcDir(rootTaskOfType<FontWrappersGenerationTask>())
}

dependencies {
    implementation(project(":core"))
}
