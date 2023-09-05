plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("version-catalogs-generator") {
            id                  = "io.github.andrew-k-21-12.version-catalogs-generator"
            version             = "1.0.0"
            implementationClass = "io.github.andrewk2112.versioncatalogsgenerator.gradle.Plugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.tomlj)
}
