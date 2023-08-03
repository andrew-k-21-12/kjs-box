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
listOf("plugins").map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
