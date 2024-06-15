plugins {
    alias(kotlinLibs.plugins.kotlin.multiplatform)
}

group   = "io.github.andrew-k-21-12.kjs-box"
version = "1.0.0"

kotlin {
    @Suppress("OPT_IN_USAGE")
    compilerOptions {
        // This is a temporary compiler argument which is going to be removed
        // when `expect`/`actual` classes become a part of stable API.
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    jvm()
    // Native targets. There are lots of unimplemented ones including Windows and Linux.
    // Only those targets which are possible to be compiled by the host OS are getting registered.
    // Otherwise, there will be lots of Gradle tasks failing to be executed due to missing system environments.
    // Also, multiple targets can be registered even if they don't exactly match the host OS configuration
    // but can be compiled by it anyway:
    // for example, if the host OS is macOS, it can compile both ARM and Intel executables.
    val isMacOs = System.getProperty("os.name") == "Mac OS X"
    buildList {
        if (isMacOs) {
            add(macosArm64())
            add(macosX64())
        } else {
            logger.info("Host OS is not [yet] supported to compile this project natively")
        }
    }.forEach {
        it.binaries.executable {
            entryPoint = "io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.main"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlinLibs.clikt)
                implementation(kotlinLibs.my.utility.common)
                implementation(kotlinLibs.okio)
            }
        }
    }
}

tasks {
    register("fatJar", Jar::class) {
        archiveBaseName                   = "${project.name}-jvm-fat"
        manifest.attributes["Main-Class"] = "io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.MainKt"
        duplicatesStrategy                = DuplicatesStrategy.EXCLUDE
        val main by kotlin.jvm().compilations.getting
        from(
            main.output.classesDirs,
            main.runtimeDependencyFiles.files.filter { it.name.endsWith("jar") }
                                             .map { zipTree(it) }
        )
    }.also {
        assemble.get().dependsOn(it)
    }
}
