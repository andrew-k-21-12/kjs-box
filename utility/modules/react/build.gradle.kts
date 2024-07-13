plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
    alias(kotlinLibs.plugins.my.githubpackagespublisher)
}

group   = "io.github.andrew-k-21-12.utility"
version = "1.0.0"

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(dependencies.platform(kotlinLibs.kotlin.wrappers.bom.get()))
                implementation(kotlinLibs.kotlin.wrappers.react)
                implementation(kotlinLibs.my.utility.common)
            }
        }
    }
}
