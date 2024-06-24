plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
    alias(kotlinLibs.plugins.my.githubpackagespublisher)
}

group   = "io.github.andrew-k-21-12.utility"
version = "1.0.0"

kotlin {
    js().browser()
    jvm {}
    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlinLibs.kotlin.test)
            }
        }
    }
}
