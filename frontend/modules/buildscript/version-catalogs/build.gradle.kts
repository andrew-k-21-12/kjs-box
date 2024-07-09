import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.string.extensions.capitalize

plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    alias(kotlinLibs.plugins.my.githubpackagespublisher)
    alias(kotlinLibs.plugins.my.versioncatalogsgenerator)
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0"

// Generating Kotlin sources for required version catalogs.
versionCatalogsGenerator {
    packageName.set("io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs")
    catalogs {
        arrayOf("kotlin", "js").forEach {
            register("${it.capitalize()}VersionCatalog") {
                file.set(layout.projectDirectory.asFile.joinWithPath("../../../../dependencies/$it.toml"))
            }
        }
    }
}

kotlin {
    sourceSets {
        // Including the generated version catalogs into the sources.
        main.get().kotlin.srcDir(versionCatalogsGenerator.codeGenerationTask)
    }
}
