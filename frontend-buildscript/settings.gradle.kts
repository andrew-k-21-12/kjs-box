// These inclusions are required to perform builds with dependencies provided locally.
includeBuild("../frontend-core")
includeBuild("../utility-gradle")
pluginManagement {
    includeBuild("../frontend-buildscript-shared")
    includeBuild("../version-catalogs-generator")
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("kotlinLibs") {
            from(files("../dependencies/kotlin.toml"))
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "frontend-buildscript"
listOf("entry-point", "lazy-module", "main", "resource-wrappers", "shared", "version-catalogs").map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
