import io.github.andrewk2112.extensions.joinWithPath
import io.github.andrewk2112.gradle.kotlinWrapper
import io.github.andrewk2112.gradle.plugins.ResourceWrappersGenerationPlugin.Companion.getGeneratedWrappersDirectory
import io.github.andrewk2112.gradle.plugins.ResourceWrappersGenerationPlugin.Companion.getResourcesWrappersBasePackageName
import io.github.andrewk2112.gradle.tasks.ImageInterfacesGenerationTask

val reactVersion:          String by project
val kotlinWrappersVersion: String by project

plugins {
    kotlin("js")
}

// How and where to generate image interfaces for image resource wrappers - a temporary solution to be dropped soon.
val generateImageInterfaces by tasks.registering(ImageInterfacesGenerationTask::class) {
    interfacesPackageName.set("${getResourcesWrappersBasePackageName()}.images")
    interfacesOutDirectory.set(getGeneratedWrappersDirectory().joinWithPath("imageInterfaces"))
}

kotlin {
    js(IR).browser()
    // Using the required generated image interfaces in the module's sources.
    sourceSets.main.get().kotlin.srcDir(generateImageInterfaces)
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
