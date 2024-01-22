plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.entrypoint)
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymoduleaccessors)
}

kotlin.sourceSets {
    val jsMain by getting {
        kotlin.srcDirs(
            lazyModuleAccessors.generateOrGetFor(projects.exercises, projects.materialDesign)
        )
        dependencies {
            implementation(projects.dependencyInjection)
            implementation(projects.dependencyInjectionUtility)
            implementation(projects.designTokens)
        }
    }
}

entryPoint {
    rootComponent.set("io.github.andrewk2112.kjsbox.frontend.example.index.app")
}
