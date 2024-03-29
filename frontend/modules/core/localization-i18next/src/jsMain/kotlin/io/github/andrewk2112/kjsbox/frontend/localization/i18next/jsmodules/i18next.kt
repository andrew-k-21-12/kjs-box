package io.github.andrewk2112.kjsbox.frontend.localization.i18next.jsmodules

// This library exports a singleton instance by default,
// so it's just needed to declare its matching methods of interest.
internal external interface I18n {
    fun use(module: dynamic): I18n
    fun init(options: dynamic)
    fun <T> on(event: String, callback: (T) -> Unit)
    fun t(key: String): String
    fun changeLanguage(lng: String)
}

@JsModule("i18next") // the name of the npm module to be bridged
@JsNonModule         // to use a distribution in both standalone downloadable piece of JavaScript
                     // and npm package module formats at the same time - required to be declared in most cases
internal external val i18next: I18n
