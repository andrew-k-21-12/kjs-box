package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.footer

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.horizontalDivider
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.FooterEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.LayoutStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.FontStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.LabelStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.SelectionStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.googleLogoIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.materialDesignLogoEmptyIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.MaterialDesignTokens
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

    val endpoints by useState { FooterEndpoints() }
    val uiState   by useState { FooterUiState(endpoints) }

    container(context) {
        upperBlock {
            logoLink(context)
            adaptiveMargins(context) {
                description(context, localizator(materialIsAdaptableSystemOfGuidelinesComponentsAndToolsKey))
                externalLinks(context, hasContentType = true) {
                    for (contentLink in uiState.contentLinks) {
                        it(localizator(contentLink.title), contentLink.destinationEndpoint)
                    }
                }
            }
        }
        divider()
        lowerBlock(context) {
            googleLink(context, endpoints.google)
            externalLinks(context, hasContentType = false) {
                for (serviceLink in uiState.serviceLinks) {
                    it(localizator(serviceLink.title), serviceLink.destinationEndpoint)
                }
            }
        }
    }

}

private inline fun ChildrenBuilder.container(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +footer(clazz = FooterStyles.background(context).name) {
        +div(clazz = FooterStyles.container(context).name, children)
    }

private inline fun ChildrenBuilder.upperBlock(crossinline children: ChildrenBuilder.() -> Unit) =
    +section(clazz = FooterStyles.upperBlock.name, children)

private fun ChildrenBuilder.logoLink(context: Context) =
    +a(clazz = SelectionStyles.simpleActionHighlighting(context).name) {
        safeBlankHref = MainMaterialEndpoints.root
        +materialDesignLogoEmptyIcon(clazz = FooterStyles.logoIcon.name)
    }

private inline fun ChildrenBuilder.adaptiveMargins(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(clazz = FooterStyles.adaptiveMargins(context).name, children)

private fun ChildrenBuilder.description(context: Context, descriptionText: String) =
    +p(clazz = FooterStyles.description(context).name) { +descriptionText }

private fun ChildrenBuilder.divider() = +horizontalDivider(clazz = FooterStyles.divider.name)

private inline fun ChildrenBuilder.lowerBlock(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +section(clazz = FooterStyles.lowerBlock(context).name, children)

private fun ChildrenBuilder.googleLink(context: Context, destinationEndpoint: String) =
    +a(clazz = FooterStyles.googleLink(context).name) {
        +googleLogoIcon(clazz = FooterStyles.googleLogo(context).name)
        safeBlankHref = destinationEndpoint
    }

private inline fun ChildrenBuilder.externalLinks(
    context: Context,
    hasContentType: Boolean,
    crossinline linksAdapter: ((title: String, destinationEndpoint: String) -> Unit) -> Unit,
) {
    val links: ChildrenBuilder.() -> Unit = {
        linksAdapter { title, destinationEndpoint ->
            externalLink(context, hasContentType = hasContentType, title, destinationEndpoint)
        }
    }
    if (hasContentType) +ul(clazz = FooterStyles.contentLinks.name, links) else ul(links)
}

private fun ChildrenBuilder.externalLink(
    context: Context,
    hasContentType: Boolean,
    title: String,
    destinationEndpoint: String,
) =
    +li(clazz = FooterStyles.run { if (hasContentType) contentLink else serviceLink }(context).name) {
        +a(clazz = FooterStyles.linkAnchor(context).name) {
            safeBlankHref = destinationEndpoint
            +title
        }
    }



// Styles.

private object FooterStyles : DynamicStyleSheet() {

    val background: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = MaterialDesignTokens.system.palette.surface3(it)
    }

    val container: DynamicCssProvider<Context> by dynamicCss {
        +LayoutStyles.contentContainer.rules
        padding = Padding(
            top        = MaterialDesignTokens.reference.spacing.run {
                             if (!it.isColumnFolded) absolute48 else absolute32
                         },
            horizontal = MaterialDesignTokens.reference.spacing.absolute40,
            bottom     = MaterialDesignTokens.reference.spacing.absolute35
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
            marginTop = MaterialDesignTokens.reference.spacing.absolute16
        } else {
            flexBasis = FlexBasis.zero
            flexGrow  = 1
            marginLeft = MaterialDesignTokens.reference.spacing.absolute32
            marginTop  = MaterialDesignTokens.reference.spacing.absolute1
        }
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize   = MaterialDesignTokens.reference.fontSizes.relative0p875
        lineHeight = MaterialDesignTokens.reference.fontSizes.lineHeight1p5
        color = MaterialDesignTokens.system.palette.onSurface3(it)
        if (!it.isColumnFolded) {
            maxWidth = 50.pct
        }
    }

    val contentLinks: NamedRuleSet by css {
        marginTop  = MaterialDesignTokens.reference.spacing.absolute34
        lineHeight = MaterialDesignTokens.reference.fontSizes.lineHeight1p7
    }

    val divider: NamedRuleSet by css {
        marginTop = MaterialDesignTokens.reference.spacing.absolute37
    }

    val lowerBlock: DynamicCssProvider<Context> by dynamicCss {
        display   = Display.flex
        marginTop = MaterialDesignTokens.reference.spacing.absolute32
        if (it.isColumnFolded) {
            flexDirection = FlexDirection.column
        } else {
            alignItems = Align.center
        }
    }

    val googleLink: DynamicCssProvider<Context> by dynamicCss {
        marginRight = MaterialDesignTokens.reference.spacing.absolute32
        if (it.isColumnFolded) {
            marginBottom = MaterialDesignTokens.reference.spacing.absolute15
        }
    }

    val googleLogo: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        color = MaterialDesignTokens.system.palette.action3(it)
    }

    val contentLink: DynamicCssProvider<Context> by dynamicCss {
        display = Display.inline
        nthChild("n+2") {
            before {
                margin = Margin(horizontal = MaterialDesignTokens.reference.spacing.absolute8)
                content = QuotedString("/")
            }
        }
    }

    val serviceLink: DynamicCssProvider<Context> by dynamicCss {
        lineHeight = MaterialDesignTokens.reference.fontSizes.lineHeight1p5
        if (it.isColumnFolded) {
            display = Display.block
        } else {
            display = Display.inline
            nthChild("n+2") {
                marginLeft = MaterialDesignTokens.reference.spacing.absolute16
            }
        }
    }

    val linkAnchor: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.link(it).rules
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
