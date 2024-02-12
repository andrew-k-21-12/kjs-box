plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0"

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(platform(kotlinLibs.kotlin.wrappers.bom.get()))
                implementation(kotlinLibs.kotlin.wrappers.react)
                implementation(kotlinLibs.kotlin.wrappers.styled.next)
                implementation(kotlinLibs.my.utility.string)
            }
        }
    }
}
