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
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:${Configs.REACT_VERSION}-${Configs.KOTLIN_WRAPPER_VERSION}")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.3-${Configs.KOTLIN_WRAPPER_VERSION}") // to declare and reuse styles directly in code
    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-${Configs.KOTLIN_WRAPPER_VERSION}")       // to use global state
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-${Configs.KOTLIN_WRAPPER_VERSION}") // in React

    // Dependency injection.
    implementation("org.kodein.di:kodein-di:7.10.0")

    // Test environment.
    testImplementation(kotlin("test"))

}

kotlin {
    js(LEGACY) { // unfortunately the new compiler can not process some type casts correctly
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}

// An Apple Silicon compilation fix.
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}
