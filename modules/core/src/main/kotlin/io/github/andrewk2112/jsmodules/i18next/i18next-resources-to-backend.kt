package io.github.andrewk2112.jsmodules.i18next

@JsModule("i18next-resources-to-backend")
@JsNonModule
internal external fun i18nextResourcesToBackend(
    resourceProvider: (language: String, namespace: String) -> dynamic
): dynamic
