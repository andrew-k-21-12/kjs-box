plugins {
    kotlin("js")
}

kotlin {
    js(IR).browser()
}

dependencies {
    implementation(project(":core"))
}
