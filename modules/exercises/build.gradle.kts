import io.github.andrewk2112.extensions.rootTaskOfType
import io.github.andrewk2112.tasks.wrappers.GenerateFontWrappersTask

plugins {
    kotlin("js")
}

val generateFontWrappers = rootTaskOfType<GenerateFontWrappersTask>()

kotlin {
    js(IR).browser()
    // Using the required generated font wrappers in the module's sources.
    sourceSets.main.get().kotlin.srcDir(
        File(generateFontWrappers.outWrappers.asFile.get(), "exercises")
    )
}

dependencies {
    implementation(project(":core"))
}

// To enable optimizations, as the compilation depends on the wrapper task.
tasks.named("compileKotlinJs").get().dependsOn(generateFontWrappers)
