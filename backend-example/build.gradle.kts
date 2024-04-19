plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
}

dependencies {
    implementation(kotlinLibs.ktor)
    implementation(kotlinLibs.ktor.compression)
    implementation(kotlinLibs.ktor.conditionalheaders)
    implementation(kotlinLibs.ktor.netty)
    implementation(kotlinLibs.slf4j.simple) // to avoid errors about a missing logging library
}

tasks {
    register("fatJar", Jar::class) {
        archiveBaseName                   = "${project.name}-fat"
        manifest.attributes["Main-Class"] = "io.github.andrewk2112.kjsbox.backend.example.MainKt"
        duplicatesStrategy                = DuplicatesStrategy.EXCLUDE
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        with(jar.get())
    }
}
