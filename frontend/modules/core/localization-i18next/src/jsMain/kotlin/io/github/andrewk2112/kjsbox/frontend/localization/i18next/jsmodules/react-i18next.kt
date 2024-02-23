package io.github.andrewk2112.kjsbox.frontend.localization.i18next.jsmodules

@JsModule("react-i18next")
@JsNonModule
internal external val reactI18next: ReactI18next

internal external interface ReactI18next {
    val initReactI18next: dynamic // we just need this single field from the module's exports
                                  // and we don't care about its type a lot
    fun useTranslation(namespace: String): dynamic
}
