plugins {
    alias(kotlinLibs.plugins.kotlin.js)
}

kotlin {
    js(IR).browser()
}

dependencies {
    implementation(projects.core)
}
