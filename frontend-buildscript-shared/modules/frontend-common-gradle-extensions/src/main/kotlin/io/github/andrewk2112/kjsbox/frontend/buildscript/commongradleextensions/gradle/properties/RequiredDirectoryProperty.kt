package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.properties

import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.utility.NotNullVar
import org.gradle.api.Task
import java.io.File
import kotlin.reflect.KProperty

/**
 * This is a [File] property delegate requiring its value to be set before any usage
 * and preventing the [Task] from being launched if the [File] doesn't point to any existing directory.
 *
 * @throws UninitializedPropertyAccessException On attempt to read the property's value before it is set.
 */
class RequiredDirectoryProperty : NotNullVar<Task, File>() {

    override fun setValue(thisRef: Task, property: KProperty<*>, value: File) {
        if (backingValue == null) {
            thisRef.onlyIf {
                backingValue?.isDirectory == true
            }
        }
        backingValue = value
    }

}
