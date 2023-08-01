import io.github.andrewk2112.kjsbox.frontend.dsl.gradle.tasks.generateLazyEntryComponent

plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-entry-point")
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
