package io.github.andrewk2112.md.components

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.md.components.common.horizontalDivider
import io.github.andrewk2112.md.models.ExternalLinkItem
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
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.ul

// Public.

val footer = VFC {

    // State.

    val context     = useAppContext()
    val localizator = useLocalizator()

    val endpoints by useState { FooterEndpoints() }
    val data      by useState { FooterData(endpoints) }



    // Rendering.

    // A background color container.
    +ReactHTML.footer(FooterStyles.coloredContainer(context).name) {

        // Positioning.
        +div(FooterStyles.container(context).name) {

            // Just for the proper positioning.
            +section(FooterStyles.upperBlock.name) {

                // Link with the logo.
                +a(SelectionStyles.simpleActionHighlighting(context).name) {
                    safeBlankHref = MainMaterialEndpoints.root
                    +iconMaterialDesignLogoEmpty.component(FooterStyles.materialIcon.name)
                }

                // Description and content links block.
                +div(FooterStyles.descriptionAndContentLinks(context).name) {

                    // Short textual description.
                    +p(FooterStyles.descriptionText(context).name) {
                        +localizator("md.materialIsAdaptableSystemOfGuidelinesComponentsAndTools")
                    }

                    // External contents links.
                    +ul(FooterStyles.contentLinks.name) {
                        for (externalContentItem in data.externalContentItems) {
                            externalLinkListItem(
                                context,
                                localizator(externalContentItem.label),
                                externalContentItem.endpoint,
                                true
                            )
                        }
                    }

                }

            }

            +horizontalDivider(FooterStyles.divider.name)

            // Google and privacy links.
            +section(FooterStyles.lowerBlock(context).name) {

                // Google link.
                +a(FooterStyles.googleLink(context).name) {
                    +iconGoogleLogo.component(FooterStyles.googleLogo(context).name)
                    safeBlankHref = endpoints.google
                }

                // All external links.
                ul {
                    for (externalLinkItem in data.externalLinkItems) {
                        externalLinkListItem(
                            context,
                            localizator(externalLinkItem.label),
                            externalLinkItem.endpoint,
                            false
                        )
                    }
                }

            }

        }

    }

}



// Private - reusable views.

private fun ChildrenBuilder.externalLinkListItem(
    context: Context,
    label: String,
    destinationEndpoint: String,
    hasContentType: Boolean
) {

    +li(FooterStyles.run { if (hasContentType) contentLink else externalLink }(context).name) {

        +a(FooterStyles.contentLinkAnchor(context).name) {
            safeBlankHref = destinationEndpoint
            +label
        }

    }

}



// Private - styles.

private object FooterStyles : DynamicStyleSheet() {

    val coloredContainer: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = Theme.palette.surface3(it)
    }

    val container: DynamicCssProvider<Context> by dynamicCss {
        +LayoutStyles.contentContainer.rules
        padding(
            top        = StyleValues.spacing.run { if (it.screenSize > SMALL_TABLET) absolute48 else absolute32 },
            horizontal = StyleValues.spacing.absolute40,
            bottom     = StyleValues.spacing.absolute35
        )
    }

    val upperBlock: NamedRuleSet by css {
        display    = Display.flex
        flexWrap   = FlexWrap.wrap
        alignItems = Align.start
    }

    val materialIcon: NamedRuleSet by css {
        display = Display.block
    }

    val descriptionAndContentLinks: DynamicCssProvider<Context> by dynamicCss {
        if (it.screenSize > SMALL_TABLET) {
            flexBasis = FlexBasis.zero
            flexGrow  = 1
            marginLeft = StyleValues.spacing.absolute32
            marginTop  = StyleValues.spacing.absolute1
        } else {
            marginTop = StyleValues.spacing.absolute16
        }
    }

    val descriptionText: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize   = StyleValues.fontSizes.relativep875
        lineHeight = StyleValues.fontSizes.lh1p5
        color = Theme.palette.onSurface3(it)
        if (it.screenSize > SMALL_TABLET) {
            maxWidth = 50.pct
        }
    }

    val contentLinks: NamedRuleSet by css {
        marginTop  = StyleValues.spacing.absolute34
        lineHeight = StyleValues.fontSizes.lh1p7
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

    val contentLinkAnchor: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.link(it).rules
        display = Display.inlineBlock // to avoid wrapping by word
    }

    val divider: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute37
    }

    val lowerBlock: DynamicCssProvider<Context> by dynamicCss {
        display    = Display.flex
        marginTop = StyleValues.spacing.absolute32
        if (it.screenSize > SMALL_TABLET) {
            alignItems = Align.center
        } else {
            flexDirection = FlexDirection.column
        }
    }

    val googleLink: DynamicCssProvider<Context> by dynamicCss {
        marginRight = StyleValues.spacing.absolute32
        if (it.screenSize <= SMALL_TABLET) {
            marginBottom = StyleValues.spacing.absolute15
        }
    }

    val googleLogo: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        color = Theme.palette.action4(it)
    }

    val externalLink: DynamicCssProvider<Context> by dynamicCss {
        lineHeight = StyleValues.fontSizes.lh1p5
        if (it.screenSize > SMALL_TABLET) {
            display = Display.inline
            nthChild("n+2") {
                marginLeft = StyleValues.spacing.absolute16
            }
        } else {
            display = Display.block
        }
    }

}



// Private - data.

private class FooterData(footerEndpoints: FooterEndpoints) {

    val externalContentItems: Array<ExternalLinkItem> = arrayOf(
        ExternalLinkItem("md.github",              footerEndpoints.github),
        ExternalLinkItem("md.twitter",             footerEndpoints.twitter),
        ExternalLinkItem("md.youtube",             footerEndpoints.youtube),
        ExternalLinkItem("md.blogRss",             footerEndpoints.rssFeed),
        ExternalLinkItem("md.subscribeForUpdates", footerEndpoints.newsletterSubscription),
    )

    val externalLinkItems: Array<ExternalLinkItem> = arrayOf(
        ExternalLinkItem("md.privacyPolicy",  footerEndpoints.privacyPolicy),
        ExternalLinkItem("md.termsOfService", footerEndpoints.termsOfService),
        ExternalLinkItem("md.feedback",       footerEndpoints.feedback),
    )

}
