// Overriding the default cleaning task by registering it explicitly to make it available from composite builds.
tasks.register("clean", Delete::class.java) {
    delete(
        layout.buildDirectory,
        subprojects.map { it.layout.buildDirectory }
    )
}
