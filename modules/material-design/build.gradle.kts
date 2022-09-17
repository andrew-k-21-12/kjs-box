import io.github.andrewk2112.Configs

plugins {
    kotlin("js")
}

kotlin {
    js(IR).browser()
    // Using the required generated image and font wrappers in the module's sources.
    sourceSets.main.get().kotlin.apply {
        srcDir(File(Configs.imageWrappersBaseDir, "md"))
        srcDir(File(Configs.fontWrappersBaseDir,  "md"))
    }
}

dependencies {
    implementation(project(":core"))
}
