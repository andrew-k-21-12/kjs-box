import io.github.andrewk2112.Configs

plugins {
    kotlin("js")
}

kotlin {
    js(IR).browser()
    // Using the required generated image wrappers in the module's sources.
    sourceSets.main.get().kotlin.srcDir(File(Configs.imageWrappersBaseDir, "md"))
}

dependencies {
    implementation(project(":core"))
}
