plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers)
}

kotlin.sourceSets {
    val jsMain by getting {
        dependencies {
            implementation(kotlinLibs.kjsbox.frontend.designtokens)
            implementation(kotlinLibs.my.utility.kodein)
            implementation(kotlinLibs.my.utility.string)
            implementation(projects.dependencyInjection)
            implementation(projects.dependencyInjectionUtility)
            implementation(projects.designTokens)
        }
    }
}

lazyModule {
    exportedComponent.set(
        "io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.dependencyInjectionAndScaffold"
    )
}
