plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers)
}

kotlin.sourceSets {
    val jsMain by getting {
        dependencies {
            implementation(kotlinLibs.kjsbox.frontend.designtokens)
            implementation(kotlinLibs.kjsbox.frontend.localization)
            implementation(kotlinLibs.my.utility.js)
            implementation(kotlinLibs.my.utility.kodein)
            implementation(kotlinLibs.my.utility.react)
            implementation(kotlinLibs.my.utility.react.dom)
            implementation(kotlinLibs.my.utility.string)
            implementation(projects.dependencyInjection)
            implementation(projects.dependencyInjectionUtility)
            implementation(projects.designTokens)
            implementation(projects.localization)
            implementation(projects.sharedUtility)
        }
    }
}

lazyModule {
    exportedComponent.set(
        "io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.dependencyInjectionAndScaffold"
    )
}
