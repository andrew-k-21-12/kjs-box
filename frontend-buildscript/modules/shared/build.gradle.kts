plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
}

dependencies {
    implementation(gradleApi())           // to access Gradle APIs
    implementation(kotlinLibs.commons.io) // to simplify some file operations a bit
    implementation(kotlinLibs.my.utility.common)
}
