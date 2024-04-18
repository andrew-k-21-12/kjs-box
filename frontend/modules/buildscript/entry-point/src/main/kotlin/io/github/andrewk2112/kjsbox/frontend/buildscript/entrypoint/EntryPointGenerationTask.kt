package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.intellij.lang.annotations.Language

/**
 * Prepares and writes an entry point source file to bootstrap a root React component.
 */
internal abstract class EntryPointGenerationTask : DefaultTask() {

    // Configurations.

    /** Full name (including package) of component to be bootstrapped as an entry point. */
    @get:Input
    internal abstract val rootComponentName: Property<String>

    /** Where to write the generated sources. */
    @get:OutputDirectory
    internal abstract val sourcesOutDirectory: RegularFileProperty



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private fun generateAndWriteEntryPointFile() = sourcesOutDirectory.get().asFile
                                                                      .joinWithPath(entryPointFileName)
                                                                      .writeText(generateEntryPointCode())



    // Configs.

    /**
     * Generates the code performing initial setups and launching the root React component.
     */
    @Language("kotlin")
    @Throws(IllegalStateException::class)
    private fun generateEntryPointCode() = """
import js.promise.catch
import kotlinx.css.*
import react.*
import react.dom.client.createRoot
import styled.injectGlobal
import web.dom.document
import web.navigator.navigator

/** The entry point - sets a root component to be rendered first. */
@EagerInitialization
@OptIn(ExperimentalStdlibApi::class)
@Suppress("unused", "DEPRECATION")
private val main = run {
    
    registerServiceWorker()

    // Injecting global styles, using the code instead of static files to get minification.
    injectGlobal(clearfixCss)

    // Looking for the target root element, starting React configuration and rendering inside.
    createRoot(document.getElementById(reactRootElementId)!!)
        .render(${rootComponentName.get()}.create())

}

private fun registerServiceWorker() {
    try {
        navigator.serviceWorker.register(serviceWorkerPath).then {
            console.info("Service worker registration succeeded:", it)
        }.catch {
            console.warn("Service worker registration failed:", it)
        }
    } catch (_: Throwable) { // no service worker API is defined
        console.warn("Service workers are not supported")
    }
}

/** Where to grab service worker configurations from. */
private inline val serviceWorkerPath get() = "./service-worker.js"

/** Basic styles to be applied to all elements (including the ones outside React components). */
private inline val clearfixCss: CssBuilder
    get() = CssBuilder(allowClasses = false).apply {
        rule("*") {
            boxSizing = BoxSizing.borderBox // width and height of boxes include borders, margins and padding
            margin    = Margin(0.px)
            padding   = Padding(0.px)
        }
    }

/** The ID of root element to render React-based contents inside. */
private inline val reactRootElementId get() = "root"

    """.trimIndent()

    /** This filename will be used as a destination to write entry point sources into. */
    private inline val entryPointFileName get() = "main.kt"

}
