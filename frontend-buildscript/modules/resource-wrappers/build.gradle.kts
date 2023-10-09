plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
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
    implementation(kotlinLibs.kotlin.gradleplugin) // to create plugins with Kotlin features
    implementation(kotlinLibs.commons.io)          // to simplify some file operations a bit
    implementation(kotlinLibs.kjsbox.frontend.commongradleextensions)
    implementation(kotlinLibs.kjsbox.frontend.versioncatalogs) // to include and reuse version catalogs
    implementation(kotlinLibs.my.commonutility)
    implementation(kotlinLibs.my.gradleutility)
}
