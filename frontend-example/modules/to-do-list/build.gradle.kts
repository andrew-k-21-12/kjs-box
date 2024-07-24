plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)
}

kotlin.sourceSets {
    val jsMain by getting {
        dependencies {
            implementation(kotlinLibs.kodein.di)
            implementation(kotlinLibs.kotlin.wrappers.react.dom)
            implementation(kotlinLibs.kotlinx.coroutines.js)
            implementation(kotlinLibs.my.utility.common)
            implementation(kotlinLibs.my.utility.coroutines.react)
            implementation(kotlinLibs.my.utility.kodein)
            implementation(kotlinLibs.my.utility.react)
        }
    }
}

lazyModule.exportedComponent = "io.github.andrewk2112.kjsbox.frontend.example.todolist.components.Root"
