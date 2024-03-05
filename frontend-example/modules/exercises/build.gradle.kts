plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers)
}

kotlin.sourceSets {
    val jsMain by getting {
        dependencies {
            implementation(platform(kotlinLibs.kotlin.wrappers.bom.get()))
            implementation(kotlinLibs.kotlin.wrappers.react.router.dom)
            implementation(kotlinLibs.kjsbox.frontend.designtokens)
            implementation(kotlinLibs.kjsbox.frontend.route)
            implementation(kotlinLibs.my.utility.react)
            implementation(projects.dependencyInjection)
            implementation(projects.dependencyInjectionUtility)
            implementation(projects.designTokens)
            implementation(projects.routes)
        }
    }
}

lazyModule {
    exportedComponent.set("io.github.andrewk2112.kjsbox.frontend.example.exercises.components.exercisesList")
}
