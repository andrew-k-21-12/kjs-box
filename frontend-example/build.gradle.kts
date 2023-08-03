plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-main")
}

// Defining a group is required for correct code generation.
group   = "io.github.andrew-k-21-12.kjs-box.frontend.example"
version = "1.0.0-SNAPSHOT"

tasks {

    // Cleaning dependency builds as well.
    named("clean").get().dependsOn(
        gradle.includedBuilds.map { it.task(":clean") }
    )

}
