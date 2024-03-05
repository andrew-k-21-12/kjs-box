includeBuild("../frontend")
pluginManagement {
    includeBuild("../utility")                    // \
    includeBuild("../version-catalogs-generator") // -- to clean everything at once by a single command
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
listOf(
    "dependency-injection",
    "dependency-injection-kodein",
    "dependency-injection-utility",
    "design-tokens",
    "exercises",
    "index",
    "localization",
    "material-design",
    "routes",
    "shared-utility",
    "spacex-crew",
).map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
