plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.entrypoint)
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymoduleaccessors)
}

kotlin.sourceSets {
    val jsMain by getting {
        kotlin.srcDirs(
            lazyModuleAccessors.generateOrGetFor(projects.exercises, projects.materialDesign, projects.spacexCrew)
        )
        dependencies {
            implementation(platform(kotlinLibs.kotlin.wrappers.bom.get()))
            implementation(kotlinLibs.kotlin.wrappers.react.router.dom)
            implementation(kotlinLibs.kjsbox.frontend.route)
            implementation(projects.designTokens)
            implementation(projects.routes)
        }
    }
}

entryPoint.rootComponent = "io.github.andrewk2112.kjsbox.frontend.example.index.app"
