package io.github.andrewk2112.kjsbox.frontend.localization.i18next.jsmodules

internal external interface I18nextResourcesToBackend {

    @JsName("default")
    fun resourcesToBackend(resourceProvider: (language: String, namespace: String) -> dynamic): dynamic

}

@JsModule("i18next-resources-to-backend")
@JsNonModule
internal external val i18nextResourcesToBackend: I18nextResourcesToBackend
