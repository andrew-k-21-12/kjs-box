import io.github.andrewk2112.extensions.rootTaskOfType
import io.github.andrewk2112.tasks.wrappers.GenerateImageWrappersTask

val reactVersion:          String by project
val kotlinWrappersVersion: String by project

val generateImageWrappers = rootTaskOfType<GenerateImageWrappersTask>()

plugins {
    kotlin("js")
}

kotlin {
    js(IR).browser()
    // Using the required generated image wrappers in the module's sources.
    sourceSets.main.get().kotlin.srcDir(
        File(generateImageWrappers.outWrappers.asFile.get(), generateImageWrappers.outPathToBaseInterfaces.get())
    )
}

dependencies {

    // React and fellows.
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$reactVersion-$kotlinWrappersVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-$kotlinWrappersVersion")      // to use global state in React
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-$kotlinWrappersVersion") // to process routes

    // Other wrappers.
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled-next:1.2.1-$kotlinWrappersVersion") // to declare and reuse styles directly in code
    implementation("org.jetbrains.kotlin-wrappers:kotlin-js:1.0.0-$kotlinWrappersVersion")          // wrappers for JS entities

    // Dependency injection.
    implementation("org.kodein.di:kodein-di:7.14.0")

    // Localization.
    implementation(npm("i18next", "21.9.2"))
    implementation(npm("react-i18next", "11.18.6"))
    implementation(npm("i18next-browser-languagedetector", "6.1.5"))
    implementation(npm("i18next-http-backend", "1.4.4")) // to download translations on demand

}

// To enable optimizations, as the compilation depends on the wrapper task.
tasks.named("compileKotlinJs").get().dependsOn(generateImageWrappers)
