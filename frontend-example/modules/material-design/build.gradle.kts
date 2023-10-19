plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers)
}

kotlin {
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlinLibs.my.utility.string)
            }
        }
    }
}

lazyModule {
    exportedComponent.set("io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.scaffold")
}
