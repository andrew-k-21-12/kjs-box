package io.github.andrewk2112.resources.endpoints

/**
 * Contains links to all main Material Design-related topics.
 * */
class MaterialDesignTopicsEndpoints {

    val introduction: String
    val aboutOurMaterialStudies: String
    val foundationOverview: String
    val environmentSurfaces: String
    val understandingLayout: String
    val understandingNavigation: String
    val colorSystem: String
    val typographySystem: String
    val aboutSound: String
    val productIconography: String
    val aboutShape: String
    val understandingMotion: String
    val interactionGestures: String
    val confirmationAcknowledgement: String
    val understandingMLPatterns: String
    val guidelinesOverview: String
    val materialThemingOverview: String
    val accessibility: String
    val platformGuidanceAndroidBars: String

    init {
        val rootWithTrailingSlash = "${MaterialDesignEndpoint().value}/"
        introduction                = "${rootWithTrailingSlash}introduction"
        aboutOurMaterialStudies     = "${rootWithTrailingSlash}material-studies/about-our-material-studies"
        foundationOverview          = "${rootWithTrailingSlash}foundation-overview"
        environmentSurfaces         = "${rootWithTrailingSlash}environment/surfaces"
        understandingLayout         = "${rootWithTrailingSlash}layout/understanding-layout"
        understandingNavigation     = "${rootWithTrailingSlash}navigation/understanding-navigation"
        colorSystem                 = "${rootWithTrailingSlash}color/the-color-system"
        typographySystem            = "${rootWithTrailingSlash}typography/the-type-system"
        aboutSound                  = "${rootWithTrailingSlash}sound/about-sound"
        productIconography          = "${rootWithTrailingSlash}iconography/product-icons"
        aboutShape                  = "${rootWithTrailingSlash}shape/about-shape"
        understandingMotion         = "${rootWithTrailingSlash}motion/understanding-motion"
        interactionGestures         = "${rootWithTrailingSlash}interaction/gestures"
        confirmationAcknowledgement = "${rootWithTrailingSlash}communication/confirmation-acknowledgement"
        understandingMLPatterns     = "${rootWithTrailingSlash}machine-learning/understanding-ml-patterns"
        guidelinesOverview          = "${rootWithTrailingSlash}guidelines-overview"
        materialThemingOverview     = "${rootWithTrailingSlash}material-theming/overview"
        accessibility               = "${rootWithTrailingSlash}usability/accessibility"
        platformGuidanceAndroidBars = "${rootWithTrailingSlash}platform-guidance/android-bars"
    }

}
