plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    alias(kotlinLibs.plugins.my.githubpackagespublisher)
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0"

dependencies {
    implementation(gradleApi())           // to access Gradle APIs
    implementation(kotlinLibs.commons.io) // to simplify some file operations a bit
    implementation(kotlinLibs.my.utility.common)
}
