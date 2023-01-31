import io.github.andrewk2112.gradle.tasks.FontWrappersGenerationTask
import io.github.andrewk2112.gradle.tasks.IconWrappersGenerationTask
import io.github.andrewk2112.gradle.tasks.ImageWrappersGenerationTask
import io.github.andrewk2112.extensions.rootTaskOfType

plugins {
    kotlin("js")
}

kotlin {
    js(IR).browser()
    // Using the required generated font, icon and image wrappers in the module's sources.
    sourceSets.main.get().kotlin.srcDirs(
        rootTaskOfType<FontWrappersGenerationTask>(),
        rootTaskOfType<IconWrappersGenerationTask>(),
        rootTaskOfType<ImageWrappersGenerationTask>()
    )
}

dependencies {
    implementation(project(":core"))
}
