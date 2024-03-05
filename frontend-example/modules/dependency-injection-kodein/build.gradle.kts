plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlinLibs.kodein.di)
                implementation(kotlinLibs.kotlinx.coroutines.js)
                implementation(kotlinLibs.my.utility.kodein)
                implementation(projects.dependencyInjection)
                implementation(projects.designTokens)
                implementation(projects.localization)
            }
        }
    }
}
