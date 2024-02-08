plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

group   = "io.github.andrew-k-21-12.utility"
version = "1.0.0"

kotlin {
    js().browser()
    jvm()
}
