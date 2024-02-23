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
                implementation(kotlinLibs.kotlin.wrappers.extensions)
                implementation(kotlinLibs.kotlin.wrappers.js)
                implementation(kotlinLibs.kjsbox.frontend.localization)
                implementation(kotlinLibs.kotlinx.coroutines.js)
                jsLibs.bundles.kjsbox.frontend.localization.i18next.get().forEach { // all required JS libraries
                    implementation(npm(it.name, it.version!!))
                }
            }
        }
    }
}
