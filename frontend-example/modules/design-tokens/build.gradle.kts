plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlinLibs.kjsbox.frontend.core)
                implementation(kotlinLibs.kjsbox.frontend.designtokens)
                implementation(kotlinLibs.kjsbox.frontend.dynamicstylesheet)
                implementation(kotlinLibs.my.utility.string)
            }
        }
    }
}
