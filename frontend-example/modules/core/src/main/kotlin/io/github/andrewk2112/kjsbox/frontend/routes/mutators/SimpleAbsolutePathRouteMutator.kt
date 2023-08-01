package io.github.andrewk2112.kjsbox.frontend.routes.mutators

/**
 * Provides a simple way to get a trivial route's [absolutePath]:
 * helpful to describe links' references targeting a route.
 */
interface SimpleAbsolutePathRouteMutator : RouteMutator, NestedRouteMutator {

    /** An absolute path targeting this route. */
    val absolutePath: String get() {
        val parentPath = when (val parent = parent) {
            is SimpleAbsolutePathRouteMutator -> parent.absolutePath
            is PathRouteMutator               -> parent.path
            is NestedRouteMutator             -> null
        }
        val currentPath = if (this is PathRouteMutator) path else null
        return when {
            parentPath  == null && currentPath == null -> "/"
            parentPath  == null                        -> currentPath ?: "/"
            currentPath == null                        -> parentPath
            else                                       -> "$parentPath$currentPath"
        }
    }

}
