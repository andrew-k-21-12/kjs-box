import io.github.andrewk2112.Configs
import io.github.andrewk2112.tasks.GenerateImageWrappersTask
import io.github.andrewk2112.tasks.GenerateNodeJsBinaryTask
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask

@Suppress("RemoveRedundantQualifierName")
plugins {
    kotlin("js") version io.github.andrewk2112.Configs.KOTLIN_VERSION
}

group   = "io.github.andrew-k-21-12"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Here we put and read image wrapper classes generated according to the original images.
val imageWrappersDir = File(buildDir, "generated/imageWrappers")

kotlin {
    js(IR) {
        binaries.executable()
        browser {}
    }
    sourceSets.main.get().kotlin.srcDir(imageWrappersDir) // using the generated image wrappers in our sources
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
    implementation(devNpm("@svgr/webpack", "6.2.1"))
    implementation(devNpm("html-webpack-plugin", "5.5.0"))
    implementation(devNpm("uglifyjs-webpack-plugin", "2.2.0"))
    implementation(devNpm("terser-webpack-plugin", "5.3.3"))
    implementation(devNpm("image-minimizer-webpack-plugin", "3.2.3"))
    implementation(devNpm("imagemin", "8.0.1"))         // the minification engine to be used for the plugin above
    implementation(devNpm("imagemin-webp", "7.0.0"))    // WebP generation
    implementation(devNpm("imagemin-optipng", "8.0.0")) // lossless PNG optimization
    implementation(devNpm("copy-webpack-plugin", "9.1.0" )) // newer versions don't work correctly with npm and Yarn
    implementation(devNpm("node-json-minify", "3.0.0"))

    // Test environment.
    testImplementation(kotlin("test"))

}

tasks {

    // Describing how to generate image wrappers:
    // where are the source images, what is the target package name and where to output the generated wrappers.
    val generateImageWrappers by registering(GenerateImageWrappersTask::class) {
        resourcesDir = kotlin.sourceSets.main.get().resources.srcDirs.first()
        pathToImages = "images"
        targetPackage.set("io.github.andrewk2112.resources.images")
        outWrappers.set(imageWrappersDir)
    }

    // Generating image wrappers on each Gradle sync and making sure they exist before the compilation.
    arrayOf(named("prepareKotlinBuildScriptModel"), named("compileKotlinJs"))
        .forEach { it.get().dependsOn(generateImageWrappers) }

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
    arrayOf(named("browserProductionRun"), named("browserProductionWebpack"))
        .forEach { it.get().dependsOn(generateNodeJsCwebpBinary, generateNodeJsOptipngBinary) }

}

// An Apple Silicon compilation fix.
rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().apply {
        nodeVersion                 = "16.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}
