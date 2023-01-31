import io.github.andrewk2112.extensions.joinWithPath
import io.github.andrewk2112.gradle.plugins.NodeJsBinariesGenerationPlugin
import io.github.andrewk2112.gradle.plugins.ResourceWrappersGenerationPlugin

group   = "io.github.andrew-k-21-12"
version = "1.0.0-SNAPSHOT"

plugins {
    kotlin("js")
}
apply<NodeJsBinariesGenerationPlugin>()   // generating Node.js binaries required for production
apply<ResourceWrappersGenerationPlugin>() // generating wrappers for resources

allprojects {
    apply("${rootProject.rootDir}/versions.gradle.kts") // making versions constants available for all modules
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
    implementation(devNpm("@svgr/webpack", "6.5.1"))
    implementation(devNpm("html-webpack-plugin", "5.5.0"))
    implementation(devNpm("terser-webpack-plugin", "5.3.6"))
    implementation(devNpm("image-minimizer-webpack-plugin", "3.8.1"))
    implementation(devNpm("imagemin", "8.0.1"))         // the minification engine to be used for the plugin above
    implementation(devNpm("imagemin-webp", "7.0.0"))    // WebP generation
    implementation(devNpm("imagemin-optipng", "8.0.0")) // lossless PNG optimization
    implementation(devNpm("copy-webpack-plugin", "11.0.0"))
    implementation(devNpm("node-json-minify", "3.0.0"))

}

tasks {

    // To avoid optimization issues because of the custom modules structure.
    named("browserDevelopmentRun").get().dependsOn(named("developmentExecutableCompileSync"))
    named("browserProductionRun").get().dependsOn(named("productionExecutableCompileSync"))

    // Cleaning up the buildSrc build files as well.
    named("clean").get().doLast {
        projectDir.joinWithPath("buildSrc").joinWithPath("build").deleteRecursively()
    }

}
