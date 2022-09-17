import io.github.andrewk2112.Configs

plugins {
    kotlin("js")
}

kotlin {
    js(IR).browser()
    // Using the required generated font wrappers in the module's sources.
    sourceSets.main.get().kotlin.apply {
        srcDir(File(Configs.fontWrappersBaseDir, "exercises"))
    }
}

dependencies {
    implementation(project(":core"))
}
