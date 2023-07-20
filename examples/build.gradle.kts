import io.github.andrewk2112.kjsbox.frontend.extensions.joinWithPath

plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-main")
}

// Defining a group is required for correct code generation.
group   = "io.github.andrew-k-21-12.kjs-box.examples.frontend"
version = "1.0.0-SNAPSHOT"

tasks {

    // Not the cleanest but working way to remove the build directory of dependent DSL project as well.
    named("clean").get().doLast {
        projectDir.parentFile?.joinWithPath("frontend")?.joinWithPath("build")?.deleteRecursively()
    }

}
