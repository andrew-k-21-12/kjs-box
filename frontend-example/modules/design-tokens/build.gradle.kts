plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

kotlin {
    js().browser()
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(dependencies.platform(kotlinLibs.kotlin.wrappers.bom.get()))
                implementation(kotlinLibs.kotlin.wrappers.css)
                implementation(kotlinLibs.kotlin.wrappers.react)
                implementation(kotlinLibs.kjsbox.frontend.designtokens)
                implementation(kotlinLibs.kjsbox.frontend.dynamicstylesheet)
                implementation(kotlinLibs.my.utility.react)
                implementation(kotlinLibs.my.utility.string)
            }
        }
    }
}
