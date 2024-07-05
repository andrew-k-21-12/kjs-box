plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
    alias(kotlinLibs.plugins.my.githubpackagespublisher)
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0"

kotlin.js().browser()
