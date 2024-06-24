includeBuild(".") // to include local projects as dependencies by their string identifiers
pluginManagement {
    includeBuild("../github-packages-publisher")
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

rootProject.name = "utility"
listOf("bytes", "common", "coroutines-react", "gradle", "js", "kodein", "react", "react-dom", "string").map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
