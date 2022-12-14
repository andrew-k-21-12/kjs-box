import io.github.andrewk2112.extensions.rootTaskOfType
import io.github.andrewk2112.tasks.wrappers.GenerateImageWrappersTask
import io.github.andrewk2112.utility.kotlinWrapper

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

    // Kotlin Wrappers BOM to ensure consistency between the modules and version compatibility.
    implementation(enforcedPlatform(kotlinWrapper("wrappers-bom:$kotlinWrappersVersion")))

    // React and fellows.
    implementation(kotlinWrapper("react"))
    implementation(kotlinWrapper("react-redux"))      // to use global state in React
    implementation(kotlinWrapper("react-router-dom")) // to process routes
    implementation(npm("react", reactVersion))        // to avoid warnings about unmet peer dependencies

    // Other wrappers.
    implementation(kotlinWrapper("styled-next")) // to declare and reuse styles directly in code
    implementation(kotlinWrapper("js"))          // wrappers for JS entities

    // Dependency injection.
    implementation("org.kodein.di:kodein-di:7.16.0")

    // Localization.
    implementation(npm("i18next", "22.4.6"))
    implementation(npm("react-i18next", "12.1.1"))
    implementation(npm("i18next-browser-languagedetector", "7.0.1"))
    implementation(npm("i18next-http-backend", "2.1.1")) // to download translations on demand

}

// To enable optimizations, as the compilation depends on the wrapper task.
tasks.named("compileKotlinJs").get().dependsOn(generateImageWrappers)
