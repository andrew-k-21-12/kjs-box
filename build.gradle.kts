import io.github.andrewk2112.Configs
import io.github.andrewk2112.tasks.GenerateNodeJsBinaryTask
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask

@Suppress("RemoveRedundantQualifierName")
plugins {
    kotlin("jvm")                 version io.github.andrewk2112.Configs.KOTLIN_VERSION apply false // is needed for the child module
    kotlin("multiplatform")       version io.github.andrewk2112.Configs.KOTLIN_VERSION
    id("com.google.devtools.ksp") version io.github.andrewk2112.Configs.KSP_VERSION // Kotlin Symbol Processing features
}

group   = "io.github.andrew-k-21-12"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {}
    }
    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val jsMain by getting {
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
                implementation(devNpm("@svgr/webpack", "6.2.1"))
                implementation(devNpm("html-webpack-plugin", "5.5.0"))
                implementation(devNpm("uglifyjs-webpack-plugin", "2.2.0"))
                implementation(devNpm("terser-webpack-plugin", "5.3.3"))
                implementation(devNpm("image-minimizer-webpack-plugin", "3.2.3"))
                implementation(devNpm("imagemin", "8.0.1"))         // the minification engine to be used for the plugin above
                implementation(devNpm("imagemin-webp", "7.0.0"))    // WEBP generation
                implementation(devNpm("imagemin-optipng", "8.0.0")) // lossless PNG optimization
                implementation(devNpm("copy-webpack-plugin", "9.1.0" )) // newer versions don't work correctly with npm and Yarn
                implementation(devNpm("node-json-minify", "3.0.0"))

            }
        }
    }
}

tasks {

    val kotlinNodeJsSetup = named("kotlinNodeJsSetup", NodeJsSetupTask::class)

    // Looks for a Node.js binary - maybe there are simpler ways to execute Node.js commands.
    val findNodeJsBinary by registering {
        dependsOn(kotlinNodeJsSetup)
        outputs.file(
            File(
                kotlinNodeJsSetup.get().destination,
                "bin/node"
            )
        )
    }

    // Makes Node.js generate the required binaries manually, otherwise the image minification won't succeed.
    val generateNodeJsCwebpBinary by registering(GenerateNodeJsBinaryTask::class) {
        libName = "cwebp"
        nodeJsBinary.set(findNodeJsBinary.get().outputs.files.singleFile)
    }
    val generateNodeJsOptipngBinary by registering(GenerateNodeJsBinaryTask::class) {
        libName = "optipng"
        nodeJsBinary.set(findNodeJsBinary.get().outputs.files.singleFile)
    }

    // Production builds require the image minification binaries.
    arrayOf(named("jsBrowserProductionRun"), named("jsBrowserProductionWebpack"))
        .forEach { it.get().dependsOn(generateNodeJsCwebpBinary, generateNodeJsOptipngBinary) }

}

// An Apple Silicon compilation fix.
rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().apply {
        nodeVersion                 = "16.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}
