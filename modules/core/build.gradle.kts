import io.github.andrewk2112.Configs
import io.github.andrewk2112.Configs.KOTLIN_WRAPPER_VERSION
import io.github.andrewk2112.Configs.REACT_VERSION

plugins {
    kotlin("js")
}

kotlin {
    js(IR).browser()
    // Using the required generated image wrappers in the module's sources.
    sourceSets.main.get().kotlin.srcDir(File(Configs.imageWrappersBaseDir, Configs.BASE_IMAGE_INTERFACES_PATH))
}

dependencies {

    // React and fellows.
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$REACT_VERSION-$KOTLIN_WRAPPER_VERSION")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-$KOTLIN_WRAPPER_VERSION")      // to use global state in React
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-$KOTLIN_WRAPPER_VERSION") // to process routes

    // Other wrappers.
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled-next:1.2.1-$KOTLIN_WRAPPER_VERSION") // to declare and reuse styles directly in code
    implementation("org.jetbrains.kotlin-wrappers:kotlin-js:1.0.0-$KOTLIN_WRAPPER_VERSION")          // wrappers for JS entities

    // Dependency injection.
    implementation("org.kodein.di:kodein-di:7.14.0")

    // Localization.
    implementation(npm("i18next", "21.8.10"))
    implementation(npm("react-i18next", "11.17.2"))
    implementation(npm("i18next-browser-languagedetector", "6.1.4"))
    implementation(npm("i18next-http-backend", "1.4.1")) // to download translations on demand

}
