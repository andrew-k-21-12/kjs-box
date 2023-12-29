// To include local projects as dependencies by their string identifiers.
includeBuild(".")

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
listOf("common", "gradle", "kodein", "string").map {
    include(it)
    project(":$it").projectDir = File("modules", it)
}
