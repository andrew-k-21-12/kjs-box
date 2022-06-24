plugins {
    kotlin("js") version(Configs.KOTLIN_VERSION)
}

group   = "io.github.andrew-k-21-12"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // React and fellows.
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:${Configs.REACT_VERSION}-${Configs.KOTLIN_WRAPPER_VERSION}")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-${Configs.KOTLIN_WRAPPER_VERSION}")      // to use global state in React
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-${Configs.KOTLIN_WRAPPER_VERSION}") // to process routes
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled-next:1.2.1-${Configs.KOTLIN_WRAPPER_VERSION}")      // to declare and reuse styles directly in code

    // Dependency injection.
    implementation("org.kodein.di:kodein-di:7.13.0")

    // Localization.
    implementation(npm("i18next", "21.8.10"))
    implementation(npm("react-i18next", "11.17.2"))
    implementation(npm("i18next-browser-languagedetector", "6.1.4"))
    implementation(npm("i18next-http-backend", "1.4.1")) // to download translations on demand

    // Bundling.
    implementation(devNpm("html-webpack-plugin", "5.5.0"))
    implementation(devNpm("uglifyjs-webpack-plugin", "2.2.0"))
    implementation(devNpm("terser-webpack-plugin", "5.3.3"))
    implementation(devNpm("copy-webpack-plugin", "9.1.0" )) // newer versions don't work correctly with npm and Yarn
    implementation(devNpm("node-json-minify", "3.0.0"))
    implementation(devNpm("@svgr/webpack", "6.2.1"))

    // Test environment.
    testImplementation(kotlin("test"))

}

kotlin {
    js(IR) {
        binaries.executable()
        browser {}
    }
}

// An Apple Silicon compilation fix.
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().apply {
        nodeVersion                 = "16.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}
