plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlinLibs.kjsbox.frontend.core)
                implementation(kotlinLibs.kjsbox.frontend.localization)
                implementation(kotlinLibs.kjsbox.frontend.localization.i18next)
                implementation(kotlinLibs.kotlinx.coroutines.js)
            }
        }
    }
}
