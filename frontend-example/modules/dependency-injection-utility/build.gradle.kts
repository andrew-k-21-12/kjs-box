plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(platform(kotlinLibs.kotlin.wrappers.bom.get()))
                implementation(kotlinLibs.kotlin.wrappers.react)
                implementation(kotlinLibs.kjsbox.frontend.core)
                implementation(projects.dependencyInjection)
                implementation(projects.designTokens)
            }
        }
    }
}
