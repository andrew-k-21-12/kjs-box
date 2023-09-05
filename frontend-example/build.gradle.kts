plugins {
    alias(kotlinLibs.plugins.kjsbox.frontend.main)
    alias(kotlinLibs.plugins.kjsbox.frontend.entrypoint)       apply false
    alias(kotlinLibs.plugins.kjsbox.frontend.lazymodule)       apply false
    alias(kotlinLibs.plugins.kjsbox.frontend.resourcewrappers) apply false
}

// Defining a group is required for correct code generation.
group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0-SNAPSHOT"

tasks {

    // Cleaning dependency builds as well.
    named("clean").get().dependsOn(
        gradle.includedBuilds.map { it.task(":clean") }
    )

}
