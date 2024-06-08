plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

group   = "io.github.andrew-k-21-12.utility"
version = "1.0.0"

kotlin {
    js().browser()
    jvm()
    // All native desktop compilation targets.
    // It's possible just to enumerate the required ones
    // because these targets do not contain any platform-specific code
    // (so the compilation will succeed on any host OS).
    linuxArm64()
    linuxX64()
    macosArm64()
    macosX64()
    mingwX64()
}
