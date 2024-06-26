plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    alias(kotlinLibs.plugins.my.githubpackagespublisher)
}

group   = "io.github.andrew-k-21-12.utility"
version = "1.0.0"

dependencies {
    implementation(gradleApi())
    implementation(kotlinLibs.kotlin.gradleplugin)
    implementation(kotlinLibs.my.utility.common)
    implementation(kotlinLibs.my.utility.string)
}
