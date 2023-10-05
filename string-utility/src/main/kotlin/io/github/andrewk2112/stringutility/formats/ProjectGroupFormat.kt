package io.github.andrewk2112.stringutility.formats

/**
 * Formats [String]s as project identity groups: dots are used as dividers, no other modifications.
 */
object ProjectGroupFormat : Format() {
    
    override fun postProcess(source: String): String = source
    
    override val divider: String = "."
    
}
