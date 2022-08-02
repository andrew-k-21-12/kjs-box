import io.github.andrewk2112.Configs

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {

    // Includes KSP classes to be extended for creation of custom KSP processors.
    implementation("com.google.devtools.ksp:symbol-processing-api:${Configs.KSP_VERSION}")

}
