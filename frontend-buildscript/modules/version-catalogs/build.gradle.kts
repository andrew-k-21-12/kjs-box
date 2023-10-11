plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    alias(kotlinLibs.plugins.my.versioncatalogsgenerator)
}

// Generating Kotlin sources for required version catalogs.
versionCatalogsGenerator {
    packageName.set("io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs")
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
