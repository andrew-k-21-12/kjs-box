import io.github.andrewk2112.extensions.joinWithPath
import io.github.andrewk2112.gradle.extensions.npm
import io.github.andrewk2112.gradle.plugins.ResourceWrappersGenerationPlugin.Companion.getGeneratedWrappersDirectory
import io.github.andrewk2112.gradle.plugins.ResourceWrappersGenerationPlugin.Companion.getResourcesWrappersBasePackageName
import io.github.andrewk2112.gradle.tasks.ImageInterfacesGenerationTask

plugins {
    alias(kotlinLibs.plugins.kotlin.js)
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
    implementation(enforcedPlatform(kotlinLibs.kotlin.wrappers.bom))

    // React and fellows.
    implementation(kotlinLibs.kotlin.wrappers.react)
    implementation(kotlinLibs.kotlin.wrappers.react.redux)      // to use global state in React
    implementation(kotlinLibs.kotlin.wrappers.react.router.dom) // to process routes
    implementation(npm(jsLibs.react))                           // to avoid warnings about unmet peer dependencies

    // Other wrappers.
    implementation(kotlinLibs.kotlin.wrappers.styled.next) // to declare and reuse styles directly in code
    implementation(kotlinLibs.kotlin.wrappers.js)          // wrappers for JS entities

    // Dependency injection.
    implementation(kotlinLibs.kodein.di)

    // Localization.
    implementation(npm(jsLibs.i18next.core)) // the library itself doesn't provide any means to remove unused resources
    implementation(npm(jsLibs.i18next.react))
    implementation(npm(jsLibs.i18next.languagedetector))
    implementation(npm(jsLibs.i18next.backend)) // to bundle and download translations on demand

}
