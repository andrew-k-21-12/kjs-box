plugins {
    base // creates base lifecycle tasks to avoid issues with extending the `clean` task
}

// Overriding the default cleaning task to clean all nested subprojects from composite builds.
tasks.named("clean", Delete::class.java) {
    delete(
        layout.buildDirectory,
        subprojects.map { it.layout.buildDirectory }
    )
}
