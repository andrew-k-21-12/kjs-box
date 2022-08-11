package io.github.andrewk2112.jsmodules.i18next

@JsModule("i18next-browser-languagedetector")
@JsNonModule
external val i18nextBrowserLanguageDetector: I18nextBrowserLanguageDetector

external interface I18nextBrowserLanguageDetector {

    // This is a super counter-intuitive way to get the class declaration from the named field.
    // Unfortunately, it's unclear whether ways to bind exactly a class type exist.
    @JsName("default")
    val LanguageDetector: dynamic

}
