plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("kjs-box-frontend-lazy-module") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-lazy-module"
            version             = "1.0.0"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.buildscript.lazymodule.LazyModulePlugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.kotlin.gradleplugin) // to create plugins with Kotlin features
    implementation(kotlinLibs.kjsbox.frontend.commongradleextensions)
    implementation(kotlinLibs.kjsbox.frontend.versioncatalogs) // to include and reuse version catalogs
}