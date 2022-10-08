import io.github.andrewk2112.tasks.wrappers.GenerateFontWrappersTask
import io.github.andrewk2112.tasks.wrappers.GenerateImageWrappersTask
import io.github.andrewk2112.extensions.rootTaskOfType

plugins {
    kotlin("js")
}

val generateImageWrappers = rootTaskOfType<GenerateImageWrappersTask>()
val generateFontWrappers  = rootTaskOfType<GenerateFontWrappersTask>()

kotlin {
    js(IR).browser()
    // Using the required generated image and font wrappers in the module's sources.
    sourceSets.main.get().kotlin.srcDirs(
        File(generateImageWrappers.outWrappers.asFile.get(), "md"),
        File(generateFontWrappers.outWrappers.asFile.get(),  "md")
    )
}

dependencies {
    implementation(project(":core"))
}

// To enable optimizations, as the compilation depends on the wrapper tasks.
tasks.named("compileKotlinJs").get()
     .dependsOn(generateImageWrappers, generateFontWrappers)
