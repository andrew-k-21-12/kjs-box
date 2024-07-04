plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.entrypoint)
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymoduleaccessors)
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers)
}

kotlin.sourceSets {
    val jsMain by getting {
        kotlin.srcDirs(
            lazyModuleAccessors.generateOrGetFor(projects.exercises, projects.materialDesign, projects.spacexCrew)
        )
        dependencies {
            implementation(kotlinLibs.kotlin.wrappers.react.router.dom)
            implementation(kotlinLibs.kjsbox.frontend.route)
            implementation(projects.dependencyInjectionUtility)
            implementation(projects.designTokens)
            implementation(projects.routes)
        }
    }
}

entryPoint.rootComponent = "io.github.andrewk2112.kjsbox.frontend.example.index.App"
