package io.github.andrewk2112.utility.gradle.extensions

import io.github.andrewk2112.utility.common.utility.LazyReadOnlyProperty
import io.github.andrewk2112.utility.string.formats.cases.KebabCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import io.github.andrewk2112.utility.string.formats.other.PackageName
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper



// Configuration.

/**
 * Applies and configures the Kotlin Multiplatform plugin.
 */
@Throws(UnknownDomainObjectException::class)
fun Project.applyMultiplatform(configuration: KotlinMultiplatformExtension.() -> Unit) {
    plugins.apply(KotlinMultiplatformPluginWrapper::class.java)
    extensions.configure("kotlin", Action(configuration))
}



// Extensions.

/**
 * Creates an extension named with its property name.
 */
@Throws(IllegalArgumentException::class)
inline fun <reified T> Project.createExtension() = LazyReadOnlyProperty<Any?, T> { property ->
    extensions.create(property.name, T::class.java)
}

/**
 * Retrieves an extension of type [T].
 */
@Throws(UnknownDomainObjectException::class)
inline fun <reified T> Project.getExtension(): T = extensions.getByType(T::class.java)



// Identity.

/**
 * Prepares an inferred package name according to the [Project]'s [Project.getGroup] and [Project.getName] values.
 */
fun Project.getPossiblePackageName(): String = group.toString().changeFormat(PackageName, PackageName) + "." +
                                               name.changeFormat(KebabCase, PackageName)



// Tasks.

/**
 * Tries to find a non-typed [Task] by its [name].
 */
@Throws(UnknownDomainObjectException::class)
fun Project.findTask(name: String): Task = tasks.getByName(name)

/**
 * Tries to find a [Task] of type [T] by its [name].
 */
@Throws(IllegalStateException::class, UnknownDomainObjectException::class)
@JvmName("findTypedTask") // to avoid collisions in Gradle
inline fun <reified T : Task> Project.findTask(name: String): T = tasks.named(name, T::class.java).get()

/**
 * Registers a simple [Task] with a [name] which executes the list of [arguments].
 */
@Throws(IllegalStateException::class, InvalidUserDataException::class)
fun Project.registerExecutionTask(name: String, vararg arguments: Any): Task =
    tasks.register(name) { task ->
        task.doLast {
            exec { it.commandLine(*arguments) }
        }
    }.get()

/**
 * Registers and configures a [Task] naming it with its property name.
 */
@Throws(IllegalStateException::class, InvalidUserDataException::class)
inline fun <reified T : Task> Project.registerTask(
    noinline configuration: T.() -> Unit
) = LazyReadOnlyProperty<Any?, T> { property ->
    tasks.register(property.name, T::class.java, configuration).get()
}
