// These inclusions are required to perform builds with dependencies provided locally.
includeBuild("../frontend-core")
pluginManagement {
    includeBuild("../frontend-buildscript-shared")
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
listOf("entry-point", "lazy-module", "main", "resource-wrappers").map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
