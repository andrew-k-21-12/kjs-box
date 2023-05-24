rootProject.name = "markup-training"

// Including all modules.
file("modules")
    .listFiles()
    ?.filter(File::isDirectory)
    ?.forEach { directory ->
        val moduleName = directory.name
        include(moduleName)
        project(":$moduleName").projectDir = directory
    }
