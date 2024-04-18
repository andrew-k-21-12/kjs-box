plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
}

dependencies {
    implementation(kotlinLibs.ktor)
    implementation(kotlinLibs.ktor.compression)
    implementation(kotlinLibs.ktor.conditionalheaders)
    implementation(kotlinLibs.ktor.netty)
}
