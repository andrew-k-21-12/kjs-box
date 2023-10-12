pluginManagement {
    includeBuild("../frontend-buildscript")
    includeBuild("../frontend-core") // used in buildscripts, won't compile without it
    includeBuild("../frontend-buildscript-shared") // \
    includeBuild("../utility")                     //  - to clean everything at once by a single command
    includeBuild("../version-catalogs-generator")  // /
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
    }
}

rootProject.name = "frontend-example"
listOf("exercises", "index", "material-design").map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
