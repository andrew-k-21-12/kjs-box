import io.github.andrewk2112.kjsbox.frontend.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.gradle.extensions.devNpm
import io.github.andrewk2112.kjsbox.frontend.gradle.plugins.NodeJsBinariesGenerationPlugin

group   = "io.github.andrew-k-21-12.kjs-box.examples.frontend"
version = "1.0.0-SNAPSHOT"

plugins {
    alias(kotlinLibs.plugins.kotlin.js)
    id("io.github.andrew-k-21-12.kjs-box.frontend-main")
}
apply<NodeJsBinariesGenerationPlugin>() // generating Node.js binaries required for production

kotlin {
    js(IR) {
        binaries.executable()
        browser {}
    }
}

dependencies {

    // Requesting the compilation of on-demand modules.
    implementation(projects.index)
    implementation(projects.exercises)
    implementation(projects.materialDesign)

    // Bundling.
    implementation(devNpm(jsLibs.webpack.svgr))
    implementation(devNpm(jsLibs.webpack.html))
    implementation(devNpm(jsLibs.webpack.terser))
    implementation(devNpm(jsLibs.webpack.imageminimizer))
    implementation(devNpm(jsLibs.imagemin.core))    // the minification engine to be used for the plugin above
    implementation(devNpm(jsLibs.imagemin.webp))    // WebP generation
    implementation(devNpm(jsLibs.imagemin.optipng)) // lossless PNG optimization
    implementation(devNpm(jsLibs.i18n.unused)) // to remove unused localizations when bundling

}

tasks {

    // To avoid optimization issues because of the custom modules structure.
    named("browserDevelopmentRun").get().dependsOn(named("developmentExecutableCompileSync"))
    named("browserProductionRun").get().dependsOn(named("productionExecutableCompileSync"))

    // Not the cleanest but working way to remove the build directory of dependent DSL project as well.
    named("clean").get().doLast {
        projectDir.parentFile?.joinWithPath("frontend")?.joinWithPath("build")?.deleteRecursively()
    }

}
