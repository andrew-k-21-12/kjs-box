plugins {
    alias(kotlinLibs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("github-packages-publisher") {
            id                  = "io.github.andrew-k-21-12.github-packages-publisher"
            version             = "1.0.0"
            implementationClass = "io.github.andrewk2112.githubpackagespublisher.GithubPackagesPublisherPlugin"
        }
    }
}

dependencies {
    implementation(kotlinLibs.kotlin.gradleplugin)
}
