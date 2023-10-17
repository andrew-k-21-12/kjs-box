plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("kjs-box-frontend-lazy-module-accessors") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-lazy-module-accessors"
            version             = "1.0.0"
            implementationClass = "io.github.andrewk2112.kjsbox.frontend.buildscript.lazymoduleaccessors.LazyModuleAccessorsPlugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.my.utility.common)
    implementation(kotlinLibs.my.utility.gradle)
    implementation(kotlinLibs.my.utility.string)
}
