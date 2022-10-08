import io.github.andrewk2112.plugins.GenerateNodeJsBinariesPlugin
import io.github.andrewk2112.plugins.GenerateResourceWrappersPlugin

group   = "io.github.andrew-k-21-12"
version = "1.0.0-SNAPSHOT"

plugins {
    kotlin("js")
}
apply<GenerateResourceWrappersPlugin>() // generating wrappers for resources
apply<GenerateNodeJsBinariesPlugin>()   // generating Node.js binaries required for production

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
    implementation(devNpm("@svgr/webpack", "6.3.1"))
    implementation(devNpm("html-webpack-plugin", "5.5.0"))
    implementation(devNpm("terser-webpack-plugin", "5.3.6"))
    implementation(devNpm("image-minimizer-webpack-plugin", "3.6.1"))
    implementation(devNpm("imagemin", "8.0.1"))         // the minification engine to be used for the plugin above
    implementation(devNpm("imagemin-webp", "7.0.0"))    // WebP generation
    implementation(devNpm("imagemin-optipng", "8.0.0")) // lossless PNG optimization
    implementation(devNpm("copy-webpack-plugin", "11.0.0"))
    implementation(devNpm("node-json-minify", "3.0.0"))

}

tasks { // to avoid optimization issues because of the custom modules structure
    named("browserDevelopmentRun").get().dependsOn(named("developmentExecutableCompileSync"))
    named("browserProductionRun").get().dependsOn(named("productionExecutableCompileSync"))
}
