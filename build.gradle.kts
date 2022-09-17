import io.github.andrewk2112.Configs
import io.github.andrewk2112.Configs.initRootProjectConfigs
import io.github.andrewk2112.tasks.GenerateNodeJsBinaryTask
import io.github.andrewk2112.tasks.wrappers.GenerateFontWrappersTask
import io.github.andrewk2112.tasks.wrappers.GenerateImageWrappersTask
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask

@Suppress("RemoveRedundantQualifierName")
plugins {
    kotlin("js") version io.github.andrewk2112.Configs.KOTLIN_VERSION
}

initRootProjectConfigs()

group   = "io.github.andrew-k-21-12"
version = "1.0.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {}
    }
}

dependencies {

    // Requesting the compilation of all end modules.
    implementation(project(":index"))
    implementation(project(":exercises"))
    implementation(project(":material-design"))

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

}

tasks {

    // Describing how to generate image wrappers:
    // where are the source images, what is the target package name and where to output the generated wrappers.
    val generateImageWrappers by registering(GenerateImageWrappersTask::class) {
        resourcesDir = kotlin.sourceSets.main.get().resources.srcDirs.first()
        pathToImages = "images"
        targetPackage.set(Configs.IMAGE_WRAPPERS_PACKAGE)
        outWrappers.set(Configs.imageWrappersBaseDir)
        outPathToBaseInterfaces.set(Configs.BASE_IMAGE_INTERFACES_PATH)
    }

    // Describing how to generate font wrappers in the similar way.
    val generateFontWrappers by registering(GenerateFontWrappersTask::class) {
        resourcesDir = kotlin.sourceSets.main.get().resources.srcDirs.first()
        pathToFonts  = "fonts"
        targetBasePackage.set(Configs.FONT_WRAPPERS_PACKAGE)
        outWrappers.set(Configs.fontWrappersBaseDir)
    }

    // Generating image and font wrappers on each Gradle sync and making sure they exist before the compilation.
    arrayOf(named("prepareKotlinBuildScriptModel"), named("compileKotlinJs"))
        .forEach { it.get().dependsOn(generateImageWrappers, generateFontWrappers) }

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
