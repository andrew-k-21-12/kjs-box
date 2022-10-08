import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply("${rootDir.parentFile}/versions.gradle.kts")
val kotlinVersion: String by project

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-io:commons-io:2.11.0") // to simplify some file operations a bit
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion") // to create plugins with Kotlin features
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}
