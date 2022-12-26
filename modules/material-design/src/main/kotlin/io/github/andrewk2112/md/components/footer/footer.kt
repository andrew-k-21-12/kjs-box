package io.github.andrewk2112.md.components.footer

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.localization.LocalizationKey
import io.github.andrewk2112.md.components.common.horizontalDivider
import io.github.andrewk2112.md.resources.endpoints.FooterEndpoints
import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.md.resources.iconGoogleLogo
import io.github.andrewk2112.md.resources.iconMaterialDesignLogoEmpty
import io.github.andrewk2112.md.styles.LayoutStyles
import io.github.andrewk2112.md.styles.FontStyles
import io.github.andrewk2112.md.styles.LabelStyles
import io.github.andrewk2112.md.styles.SelectionStyles
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.utility.safeBlankHref
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

val footer = VFC {

    val context     = useAppContext()
    val localizator = useLocalizator()

    val endpoints by useState { FooterEndpoints() }
    val uiState   by useState { FooterUiState(endpoints) }

    container(context) {
        upperBlock {
            logoLink(context)
            adaptiveMargins(context) {
                description(context, localizator("md.materialIsAdaptableSystemOfGuidelinesComponentsAndTools"))
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
    +footer(FooterStyles.background(context).name) {
        +div(FooterStyles.container(context).name, block = children)
    }

private inline fun ChildrenBuilder.upperBlock(crossinline children: ChildrenBuilder.() -> Unit) =
    +section(FooterStyles.upperBlock.name, block = children)

private fun ChildrenBuilder.logoLink(context: Context) =
    +a(SelectionStyles.simpleActionHighlighting(context).name) {
        safeBlankHref = MainMaterialEndpoints.root
        +iconMaterialDesignLogoEmpty.component(FooterStyles.logoIcon.name)
    }

private inline fun ChildrenBuilder.adaptiveMargins(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(FooterStyles.adaptiveMargins(context).name, block = children)

private fun ChildrenBuilder.description(context: Context, descriptionText: String) =
    +p(FooterStyles.description(context).name) { +descriptionText }

private fun ChildrenBuilder.divider() = +horizontalDivider(FooterStyles.divider.name)

private inline fun ChildrenBuilder.lowerBlock(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +section(FooterStyles.lowerBlock(context).name, block = children)

private fun ChildrenBuilder.googleLink(context: Context, destinationEndpoint: String) =
    +a(FooterStyles.googleLink(context).name) {
        +iconGoogleLogo.component(FooterStyles.googleLogo(context).name)
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
    if (hasContentType) +ul(FooterStyles.contentLinks.name, block = links) else ul(links)
}

private fun ChildrenBuilder.externalLink(
    context: Context,
    hasContentType: Boolean,
    title: String,
    destinationEndpoint: String,
) =
    +li(FooterStyles.run { if (hasContentType) contentLink else serviceLink }(context).name) {
        +a(FooterStyles.linkAnchor(context).name) {
            safeBlankHref = destinationEndpoint
            +title
        }
    }



// Styles.

private object FooterStyles : DynamicStyleSheet() {

    val background: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = Theme.palette.surface3(it)
    }

    val container: DynamicCssProvider<Context> by dynamicCss {
        +LayoutStyles.contentContainer.rules
        padding(
            top        = StyleValues.spacing.run { if (!it.isColumnFolded) absolute48 else absolute32 },
            horizontal = StyleValues.spacing.absolute40,
            bottom     = StyleValues.spacing.absolute35
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
            marginTop = StyleValues.spacing.absolute16
        } else {
            flexBasis = FlexBasis.zero
            flexGrow  = 1
            marginLeft = StyleValues.spacing.absolute32
            marginTop  = StyleValues.spacing.absolute1
        }
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize   = StyleValues.fontSizes.relativep875
        lineHeight = StyleValues.fontSizes.lh1p5
        color = Theme.palette.onSurface3(it)
        if (!it.isColumnFolded) {
            maxWidth = 50.pct
        }
    }

    val contentLinks: NamedRuleSet by css {
        marginTop  = StyleValues.spacing.absolute34
        lineHeight = StyleValues.fontSizes.lh1p7
    }

    val divider: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute37
    }

    val lowerBlock: DynamicCssProvider<Context> by dynamicCss {
        display   = Display.flex
        marginTop = StyleValues.spacing.absolute32
        if (it.isColumnFolded) {
            flexDirection = FlexDirection.column
        } else {
            alignItems = Align.center
        }
    }

    val googleLink: DynamicCssProvider<Context> by dynamicCss {
        marginRight = StyleValues.spacing.absolute32
        if (it.isColumnFolded) {
            marginBottom = StyleValues.spacing.absolute15
        }
    }

    val googleLogo: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        color = Theme.palette.action4(it)
    }

    val contentLink: DynamicCssProvider<Context> by dynamicCss {
        display = Display.inline
        nthChild("n+2") {
            before {
                margin(horizontal = StyleValues.spacing.absolute8)
                content = QuotedString("/")
            }
        }
    }

    val serviceLink: DynamicCssProvider<Context> by dynamicCss {
        lineHeight = StyleValues.fontSizes.lh1p5
        if (it.isColumnFolded) {
            display = Display.block
        } else {
            display = Display.inline
            nthChild("n+2") {
                marginLeft = StyleValues.spacing.absolute16
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
            ExternalLinkUiState("md.github",              footerEndpoints.github),
            ExternalLinkUiState("md.twitter",             footerEndpoints.twitter),
            ExternalLinkUiState("md.youtube",             footerEndpoints.youtube),
            ExternalLinkUiState("md.blogRss",             footerEndpoints.rssFeed),
            ExternalLinkUiState("md.subscribeForUpdates", footerEndpoints.newsletterSubscription),
        ),
        arrayOf(
            ExternalLinkUiState("md.privacyPolicy",  footerEndpoints.privacyPolicy),
            ExternalLinkUiState("md.termsOfService", footerEndpoints.termsOfService),
            ExternalLinkUiState("md.feedback",       footerEndpoints.feedback),
        )
    )

}

private class ExternalLinkUiState(val title: LocalizationKey, val destinationEndpoint: String)
