// These inclusions are required to perform builds with dependencies provided locally.
includeBuild("../frontend-core")
includeBuild("../utility")
pluginManagement {
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

rootProject.name = "frontend-buildscript"
listOf(
    "entry-point",
    "frontend-buildscript-shared",
    "frontend-buildscript-version-catalogs",
    "lazy-module",
    "lazy-module-accessors",
    "main",
    "resource-wrappers",
    "shared"
).map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
