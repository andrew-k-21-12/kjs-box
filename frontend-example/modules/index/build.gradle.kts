import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.tasks.generateLazyEntryComponent

plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.entrypoint)
}

kotlin {
    sourceSets.main.get().kotlin.srcDirs(
        listOf(
            projects.exercises,
            projects.materialDesign
        ).map {
            it.dependencyProject.generateLazyEntryComponent()
        }
    )
}

entryPoint {
    rootComponent.set("io.github.andrewk2112.kjsbox.frontend.example.index.app")
}
