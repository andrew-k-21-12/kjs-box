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
        create("jsLibs") {
            from(files("../dependencies/js.toml"))
        }
    }
}

rootProject.name = "frontend"

fun buildscriptPath(folderName: String) = "modules/buildscript/$folderName"
listOf(
    "frontend-core"                         to "modules/core",
    "entry-point"                           to buildscriptPath("entry-point"),
    "lazy-module"                           to buildscriptPath("lazy-module"),
    "lazy-module-accessors"                 to buildscriptPath("lazy-module-accessors"),
    "main"                                  to buildscriptPath("main"),
    "resource-wrappers"                     to buildscriptPath("resource-wrappers"),
    "frontend-buildscript-shared"           to buildscriptPath("shared"),
    "frontend-buildscript-version-catalogs" to buildscriptPath("version-catalogs"),
).map { (projectName, projectPath) ->
    include(projectName)
    project(":$projectName").projectDir = File(projectPath)
}
