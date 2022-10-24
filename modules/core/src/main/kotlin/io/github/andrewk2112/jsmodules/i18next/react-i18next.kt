package io.github.andrewk2112.jsmodules.i18next

@JsModule("react-i18next")
@JsNonModule
internal external val reactI18next: ReactI18next

internal external interface ReactI18next {
    val initReactI18next: dynamic // we just need this single field from the module's exports
                                  // and we don't care about its type a lot
    fun useTranslation(): Translation
}

internal external interface Translation {
    fun t(key: String): String
}
