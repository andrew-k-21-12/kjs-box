package io.github.andrewk2112.md.resources.endpoints

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
    val textFields: String

    init {
        val endpoint = MaterialDesignEndpoint()
        val designWithTrailingSlash = "${endpoint.designEndpoint}/"
        introduction                = "${designWithTrailingSlash}introduction"
        aboutOurMaterialStudies     = "${designWithTrailingSlash}material-studies/about-our-material-studies"
        foundationOverview          = "${designWithTrailingSlash}foundation-overview"
        environmentSurfaces         = "${designWithTrailingSlash}environment/surfaces"
        understandingLayout         = "${designWithTrailingSlash}layout/understanding-layout"
        understandingNavigation     = "${designWithTrailingSlash}navigation/understanding-navigation"
        colorSystem                 = "${designWithTrailingSlash}color/the-color-system"
        typographySystem            = "${designWithTrailingSlash}typography/the-type-system"
        aboutSound                  = "${designWithTrailingSlash}sound/about-sound"
        productIconography          = "${designWithTrailingSlash}iconography/product-icons"
        aboutShape                  = "${designWithTrailingSlash}shape/about-shape"
        understandingMotion         = "${designWithTrailingSlash}motion/understanding-motion"
        interactionGestures         = "${designWithTrailingSlash}interaction/gestures"
        confirmationAcknowledgement = "${designWithTrailingSlash}communication/confirmation-acknowledgement"
        understandingMLPatterns     = "${designWithTrailingSlash}machine-learning/understanding-ml-patterns"
        guidelinesOverview          = "${designWithTrailingSlash}guidelines-overview"
        materialThemingOverview     = "${designWithTrailingSlash}material-theming/overview"
        accessibility               = "${designWithTrailingSlash}usability/accessibility"
        platformGuidanceAndroidBars = "${designWithTrailingSlash}platform-guidance/android-bars"
        textFields                  = "${endpoint.rootEndpoint}/components/text-fields"
    }

}
