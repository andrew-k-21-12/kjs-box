plugins {
    kotlin("js") version "1.6.10"
}

group   = "io.github.andrew-k-21-12"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.293-kotlin-1.6.10")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.293-kotlin-1.6.10")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.3-pre.293-kotlin-1.6.10") // to declare and reuse
                                                                                              // styles directly in code
    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.293-kotlin-1.6.10")       // to use
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.293-kotlin-1.6.10") // global state
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
