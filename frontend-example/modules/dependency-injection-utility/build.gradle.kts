plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(dependencies.platform(kotlinLibs.kotlin.wrappers.bom.get()))
                implementation(kotlinLibs.kotlin.wrappers.react)
                implementation(kotlinLibs.kjsbox.frontend.localization)
                implementation(kotlinLibs.kotlinx.coroutines.js)
                implementation(kotlinLibs.my.utility.coroutines.react)
                implementation(projects.dependencyInjection)
                implementation(projects.dependencyInjectionKodein)
                implementation(projects.localization)
            }
        }
    }
}
