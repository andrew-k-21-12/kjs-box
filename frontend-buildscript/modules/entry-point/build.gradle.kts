plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("kjs-box-frontend-entry-point") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-entry-point"
            version             = "1.0.0"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint.EntryPointModulePlugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.kjsbox.frontend.commongradleextensions)
    implementation(kotlinLibs.kotlin.gradleplugin) // to create plugins with Kotlin features
    implementation(kotlinLibs.my.utility.common)
    implementation(kotlinLibs.my.utility.gradle)
    implementation(projects.shared)
    implementation(projects.versionCatalogs) // to include and reuse version catalogs
}
