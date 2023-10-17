plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.entrypoint)
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymoduleaccessors)
}

kotlin {
    sourceSets.jsMain.get().kotlin.srcDirs(
        lazyModuleAccessors.generateOrGetFor(projects.exercises, projects.materialDesign)
    )
}

entryPoint {
    rootComponent.set("io.github.andrewk2112.kjsbox.frontend.example.index.app")
}
