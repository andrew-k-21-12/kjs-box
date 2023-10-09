plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
}

group   = "io.github.andrew-k-21-12"
version = "1.0.0"

dependencies {
    implementation(gradleApi())
    implementation(kotlinLibs.kotlin.gradleplugin)
    implementation(kotlinLibs.my.utility.common)
}
