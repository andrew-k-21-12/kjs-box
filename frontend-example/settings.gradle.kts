pluginManagement {
    includeBuild("../frontend-buildscript")
    includeBuild("../frontend-core") // used in buildscripts, won't compile without it
    includeBuild("../utility")                     // \
    includeBuild("../version-catalogs-generator")  // -- to clean everything at once by a single command
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "frontend-example"
listOf("exercises", "index", "material-design").map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
