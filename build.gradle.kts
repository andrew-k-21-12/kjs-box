plugins {
    alias(kotlinLibs.plugins.kotlin.jvm) apply false
}

// Overriding the default cleaning task by registering it explicitly to make it available from composite builds.
tasks.register("clean", Delete::class.java) {
    delete(
        buildDir,
        subprojects.map { it.buildDir }
    )
}
