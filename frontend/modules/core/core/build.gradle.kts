plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0-SNAPSHOT"

kotlin {
    js().browser()
}
