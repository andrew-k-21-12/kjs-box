plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    alias(kotlinLibs.plugins.my.versioncatalogsgenerator)
    `java-gradle-plugin`
}

private val basePackageName = "io.github.andrewk2112.kjsbox.frontend.buildscript"

// Generating Kotlin sources for required version catalogs.
versionCatalogsGenerator {
    packageName.set("$basePackageName.versions")
    catalogs {
        register("KotlinVersionCatalog") {
            path.set("../dependencies/kotlin.toml")
        }
        register("JsVersionCatalog") {
            path.set("../dependencies/js.toml")
        }
    }
}

kotlin {
    sourceSets {
        // Including the generated version catalogs into the sources.
        main.get().kotlin.srcDir(versionCatalogsGenerator.codeGenerationTask)
    }
}

gradlePlugin {
    plugins {
        create("kjs-box-frontend-main") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-main"
            version             = "1.0.0"
            implementationClass = "$basePackageName.gradle.plugins.MainModulePlugin"
        }
        create("kjs-box-frontend-entry-point") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-entry-point"
            version             = "1.0.0"
            implementationClass = "$basePackageName.gradle.plugins.EntryPointModulePlugin"
        }
        create("kjs-box-frontend-lazy-module") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-lazy-module"
            version             = "1.0.0"
            implementationClass = "$basePackageName.gradle.plugins.LazyModulePlugin"
        }
        create("kjs-box-frontend-resource-wrappers") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-resource-wrappers"
            version             = "1.0.0"
            implementationClass = "$basePackageName.gradle.plugins.ResourceWrappersGenerationPlugin"
        }
        create("kjs-box-frontend-extensions") {
            id                  = "io.github.andrew-k-21-12.kjs-box.frontend-extensions"
            version             = "1.0.0"
            implementationClass = "$basePackageName.gradle.plugins.ExtensionsPlugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.kotlin.gradleplugin) // to create plugins with Kotlin features
    implementation(kotlinLibs.commons.io) // to simplify some file operations a bit
}
