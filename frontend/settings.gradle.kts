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
fun corePath(folderName: String)        = "modules/core/$folderName"
listOf(
    "entry-point"                           to buildscriptPath("entry-point"),
    "lazy-module"                           to buildscriptPath("lazy-module"),
    "lazy-module-accessors"                 to buildscriptPath("lazy-module-accessors"),
    "main"                                  to buildscriptPath("main"),
    "resource-wrappers"                     to buildscriptPath("resource-wrappers"),
    "frontend-buildscript-shared"           to buildscriptPath("shared"),
    "frontend-buildscript-version-catalogs" to buildscriptPath("version-catalogs"),
    "frontend-design-tokens"                to corePath("design-tokens"),
    "frontend-dynamic-style-sheet"          to corePath("dynamic-style-sheet"),
    "frontend-image"                        to corePath("image"),
    "frontend-localization"                 to corePath("localization"),
    "frontend-localization-i18next"         to corePath("localization-i18next"),
    "frontend-route"                        to corePath("route"),
    "frontend-utility"                      to corePath("utility"),
    "frontend-patching-deployer"            to "modules/utility/patching-deployer"
).map { (projectName, projectPath) ->
    include(projectName)
    project(":$projectName").projectDir = File(projectPath)
}
