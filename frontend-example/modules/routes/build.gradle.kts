plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

kotlin {
    js().browser()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlinLibs.kjsbox.frontend.route)
            }
        }
    }
}
