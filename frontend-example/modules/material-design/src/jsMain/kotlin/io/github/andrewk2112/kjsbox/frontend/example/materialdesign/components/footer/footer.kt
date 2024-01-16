package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.footer

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.horizontalDivider
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.FooterEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.googleLogoIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.materialDesignLogoEmptyIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.ul



// Components.

val footer = FC {

    val context     = useAppContext()
    val localizator = useLocalizator()

    val component            = useMaterialDesignComponent()
    val materialDesignTokens = component.getMaterialDesignTokens()
    val styles               = useMemoWithReferenceCount(component) { FooterStyles(materialDesignTokens) }

    val endpoints by useState { FooterEndpoints() }
    val uiState   by useState { FooterUiState(endpoints) }

    container(context, styles) {
        upperBlock(styles) {
            logoLink(context, styles, materialDesignTokens)
            adaptiveMargins(context, styles) {
                description(context, styles, localizator(materialIsAdaptableSystemOfGuidelinesComponentsAndToolsKey))
                externalLinks(context, styles, hasContentType = true) {
                    for (contentLink in uiState.contentLinks) {
                        it(localizator(contentLink.title), contentLink.destinationEndpoint)
                    }
                }
            }
        }
        divider(styles)
        lowerBlock(context, styles) {
            googleLink(context, styles, endpoints.google)
            externalLinks(context, styles, hasContentType = false) {
                for (serviceLink in uiState.serviceLinks) {
                    it(localizator(serviceLink.title), serviceLink.destinationEndpoint)
                }
            }
        }
    }

}

private inline fun ChildrenBuilder.container(
    context: Context,
    styles: FooterStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +footer(clazz = styles.background(context).name) {
        +div(clazz = styles.container(context).name, children)
    }

private inline fun ChildrenBuilder.upperBlock(styles: FooterStyles, crossinline children: ChildrenBuilder.() -> Unit) =
    +section(clazz = styles.upperBlock.name, children)

private fun ChildrenBuilder.logoLink(
    context: Context,
    styles: FooterStyles,
    materialDesignTokens: MaterialDesignTokens
) =
    +a(clazz = materialDesignTokens.component.selection.simpleActionHighlighting(context).name) {
        safeBlankHref = MainMaterialEndpoints.root
        +materialDesignLogoEmptyIcon(clazz = styles.logoIcon.name)
    }

private inline fun ChildrenBuilder.adaptiveMargins(
    context: Context,
    styles: FooterStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.adaptiveMargins(context).name, children)

private fun ChildrenBuilder.description(
    context: Context,
    styles: FooterStyles,
    descriptionText: String
) =
    +p(clazz = styles.description(context).name) { +descriptionText }

private fun ChildrenBuilder.divider(styles: FooterStyles) = +horizontalDivider(clazz = styles.divider.name)

private inline fun ChildrenBuilder.lowerBlock(
    context: Context,
    styles: FooterStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +section(clazz = styles.lowerBlock(context).name, children)

private fun ChildrenBuilder.googleLink(
    context: Context,
    styles: FooterStyles,
    destinationEndpoint: String
) =
    +a(clazz = styles.googleLink(context).name) {
        +googleLogoIcon(clazz = styles.googleLogo(context).name)
        safeBlankHref = destinationEndpoint
    }

private inline fun ChildrenBuilder.externalLinks(
    context: Context,
    styles: FooterStyles,
    hasContentType: Boolean,
    crossinline linksAdapter: ((title: String, destinationEndpoint: String) -> Unit) -> Unit,
) {
    val links: ChildrenBuilder.() -> Unit = {
        linksAdapter { title, destinationEndpoint ->
            externalLink(context, styles, hasContentType = hasContentType, title, destinationEndpoint)
        }
    }
    if (hasContentType) +ul(clazz = styles.contentLinks.name, links) else ul(links)
}

private fun ChildrenBuilder.externalLink(
    context: Context,
    styles: FooterStyles,
    hasContentType: Boolean,
    title: String,
    destinationEndpoint: String,
) =
    +li(clazz = styles.run { if (hasContentType) contentLink else serviceLink }(context).name) {
        +a(clazz = styles.linkAnchor(context).name) {
            safeBlankHref = destinationEndpoint
            +title
        }
    }



// Styles.

private class FooterStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val background: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = materialDesignTokens.system.palette.surface3(it)
    }

    val container: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.layout.contentContainer.rules
        padding = Padding(
            top        = materialDesignTokens.reference.spacing.run {
                             if (!it.isColumnFolded) absolute48 else absolute32
                         },
            horizontal = materialDesignTokens.reference.spacing.absolute40,
            bottom     = materialDesignTokens.reference.spacing.absolute35
        )
    }

    val upperBlock: NamedRuleSet by css {
        display    = Display.flex
        flexWrap   = FlexWrap.wrap
        alignItems = Align.start
    }

    val logoIcon: NamedRuleSet by css {
        display = Display.block
    }

    val adaptiveMargins: DynamicCssProvider<Context> by dynamicCss {
        if (it.isColumnFolded) {
            marginTop = materialDesignTokens.reference.spacing.absolute16
        } else {
            flexBasis = FlexBasis.zero
            flexGrow  = 1
            marginLeft = materialDesignTokens.reference.spacing.absolute32
            marginTop  = materialDesignTokens.reference.spacing.absolute1
        }
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.light(it).rules
        fontSize   = materialDesignTokens.reference.fontSizes.relative0p875
        lineHeight = materialDesignTokens.reference.fontSizes.lineHeight1p5
        color = materialDesignTokens.system.palette.onSurface3(it)
        if (!it.isColumnFolded) {
            maxWidth = 50.pct
        }
    }

    val contentLinks: NamedRuleSet by css {
        marginTop  = materialDesignTokens.reference.spacing.absolute34
        lineHeight = materialDesignTokens.reference.fontSizes.lineHeight1p7
    }

    val divider: NamedRuleSet by css {
        marginTop = materialDesignTokens.reference.spacing.absolute37
    }

    val lowerBlock: DynamicCssProvider<Context> by dynamicCss {
        display   = Display.flex
        marginTop = materialDesignTokens.reference.spacing.absolute32
        if (it.isColumnFolded) {
            flexDirection = FlexDirection.column
        } else {
            alignItems = Align.center
        }
    }

    val googleLink: DynamicCssProvider<Context> by dynamicCss {
        marginRight = materialDesignTokens.reference.spacing.absolute32
        if (it.isColumnFolded) {
            marginBottom = materialDesignTokens.reference.spacing.absolute15
        }
    }

    val googleLogo: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        color = materialDesignTokens.system.palette.action3(it)
    }

    val contentLink: DynamicCssProvider<Context> by dynamicCss {
        display = Display.inline
        nthChild("n+2") {
            before {
                margin = Margin(horizontal = materialDesignTokens.reference.spacing.absolute8)
                content = QuotedString("/")
            }
        }
    }

    val serviceLink: DynamicCssProvider<Context> by dynamicCss {
        lineHeight = materialDesignTokens.reference.fontSizes.lineHeight1p5
        if (it.isColumnFolded) {
            display = Display.block
        } else {
            display = Display.inline
            nthChild("n+2") {
                marginLeft = materialDesignTokens.reference.spacing.absolute16
            }
        }
    }

    val linkAnchor: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.link(it).rules
        display = Display.inlineBlock // to avoid wrapping by word
    }

}

private inline val Context.isColumnFolded: Boolean get() = screenSize <= SMALL_TABLET



// UI state.

private class FooterUiState private constructor(
    val contentLinks: Array<ExternalLinkUiState>,
    val serviceLinks: Array<ExternalLinkUiState>,
) {

    constructor(footerEndpoints: FooterEndpoints) : this(
        arrayOf(
            ExternalLinkUiState(githubKey,              footerEndpoints.github),
            ExternalLinkUiState(twitterKey,             footerEndpoints.twitter),
            ExternalLinkUiState(youtubeKey,             footerEndpoints.youtube),
            ExternalLinkUiState(blogRssKey,             footerEndpoints.rssFeed),
            ExternalLinkUiState(subscribeForUpdatesKey, footerEndpoints.newsletterSubscription),
        ),
        arrayOf(
            ExternalLinkUiState(privacyPolicyKey,  footerEndpoints.privacyPolicy),
            ExternalLinkUiState(termsOfServiceKey, footerEndpoints.termsOfService),
            ExternalLinkUiState(feedbackKey,       footerEndpoints.feedback),
        )
    )

}

private class ExternalLinkUiState(val title: LocalizationKey, val destinationEndpoint: String)
