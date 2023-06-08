package io.github.andrewk2112.jsmodules.i18next

@JsModule("i18next-browser-languagedetector")
@JsNonModule
internal external val i18nextBrowserLanguageDetector: I18nextBrowserLanguageDetector

internal external interface I18nextBrowserLanguageDetector {

    // This is a super counter-intuitive way to get a class declaration from a named field.
    // Unfortunately, it's unclear whether ways to bind exactly a class type exist.
    @JsName("default")
    val LanguageDetector: dynamic

}
