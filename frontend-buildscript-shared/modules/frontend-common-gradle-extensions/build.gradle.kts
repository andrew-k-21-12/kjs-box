plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0"

dependencies {
    implementation(kotlinLibs.kotlin.gradleplugin) // to configure modules with Kotlin features
    implementation(kotlinLibs.commons.io)          // to simplify some file operations a bit
}
