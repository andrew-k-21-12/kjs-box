plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlinLibs.kjsbox.frontend.localization)
                implementation(kotlinLibs.kjsbox.frontend.localization.i18next)
                implementation(kotlinLibs.kjsbox.frontend.utility)
                implementation(kotlinLibs.kotlinx.coroutines.js)
            }
        }
    }
}
