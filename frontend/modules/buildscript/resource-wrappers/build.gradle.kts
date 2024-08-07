plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    alias(kotlinLibs.plugins.my.githubpackagespublisher)
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("kjs-box-frontend-resource-wrappers") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-resource-wrappers"
            version             = "1.0.0"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.plugins.ResourceWrappersGenerationPlugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.commons.io) // to simplify some file operations a bit
    implementation(kotlinLibs.kjsbox.frontend.buildscript.versioncatalogs) // to include and reuse version catalogs
    implementation(kotlinLibs.kotlin.gradleplugin) // to create plugins with Kotlin features
    implementation(kotlinLibs.my.utility.bytes)
    implementation(kotlinLibs.my.utility.common)
    implementation(kotlinLibs.my.utility.gradle)
    implementation(kotlinLibs.my.utility.string)
    testImplementation(kotlinLibs.kotlin.test)
}
