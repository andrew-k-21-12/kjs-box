plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
}

group   = "io.github.andrew-k-21-12"
version = "1.0.0"

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlinLibs.kotlin.test)
}
