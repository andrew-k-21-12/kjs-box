pluginManagement {
    includeBuild("../frontend-buildscript")
}

dependencyResolutionManagement {
    // Can be restored when https://youtrack.jetbrains.com/issue/KT-55620 is resolved.
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("kotlinLibs") {
            from(files("../dependencies/kotlin.toml"))
        }
        create("jsLibs") {
            from(files("../dependencies/js.toml"))
        }
    }
}

rootProject.name = "frontend-core"
