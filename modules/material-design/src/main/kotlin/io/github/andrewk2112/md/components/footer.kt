package io.github.andrewk2112.md.components

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.jsmodules.svg.invoke
import io.github.andrewk2112.md.components.common.horizontalDivider
import io.github.andrewk2112.md.models.ExternalLinkItem
import io.github.andrewk2112.md.resources.endpoints.FooterEndpoints
import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.md.resources.iconGoogleLogo
import io.github.andrewk2112.md.resources.iconMaterialDesignLogoEmpty
import io.github.andrewk2112.md.styles.FontStyles
import io.github.andrewk2112.md.styles.LabelStyles
import io.github.andrewk2112.md.styles.SelectionStyles
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.utility.safeBlankHref
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.ul
import react.useState

// Public.

val footer = FC<Props> {

    // State.

    val context     = useAppContext()
    val localizator = useLocalizator()

    val endpoints by useState { FooterEndpoints() }
    val data      by useState { FooterData(endpoints) }



    // Rendering.

    // Container with a basic style.
    +ReactHTML.footer(FooterStyles.container(context).name) {

        // Just for the proper positioning.
        +section(FooterStyles.iconWithDescriptions.name) {

            // Link with the logo.
            +a(SelectionStyles.simpleActionHighlighting(context).name) {
                safeBlankHref = MainMaterialEndpoints.root
                +iconMaterialDesignLogoEmpty()
            }

            // Descriptions block.
            +div(FooterStyles.descriptions.name) {

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
        +section(FooterStyles.privacyLinks.name) {

            // Google link.
            +a(FooterStyles.googleLink.name) {
                +iconGoogleLogo.component(FooterStyles.googleLogo(context).name)
                safeBlankHref = endpoints.google
            }

            // All external links.
            ul {
                for (externalLinkItem in data.externalLinkItems) {
                    externalLinkListItem(context, localizator(externalLinkItem.label), externalLinkItem.endpoint, false)
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

    +li(FooterStyles.run { if (hasContentType) contentLink else externalLink }.name) {

        +a(LabelStyles.link(context).name) {
            safeBlankHref = destinationEndpoint
            +label
        }

    }

}



// Private - styles.

private object FooterStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        padding(
            top        = StyleValues.spacing.absolute48,
            horizontal = StyleValues.spacing.absolute40,
            bottom     = StyleValues.spacing.absolute35
        )
        backgroundColor = Theme.palette.surface3(it)
    }

    val iconWithDescriptions: NamedRuleSet by css {
        display    = Display.flex
        alignItems = Align.start
    }

    val descriptions: NamedRuleSet by css {
        marginLeft = StyleValues.spacing.absolute32
        marginTop  = StyleValues.spacing.absolute1
    }

    val descriptionText: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize   = StyleValues.fontSizes.relativep9
        lineHeight = StyleValues.fontSizes.lh1p5
        color = Theme.palette.onSurface3(it)
        maxWidth = 50.pct
    }

    val contentLinks: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute34
    }

    val contentLink: NamedRuleSet by css {
        display = Display.inline
        nthChild("n+2") {
            before {
                margin(horizontal = StyleValues.spacing.absolute8)
                content = QuotedString("/")
            }
        }
    }

    val divider: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute37
    }

    val privacyLinks: NamedRuleSet by css {
        display    = Display.flex
        alignItems = Align.center
        marginTop = StyleValues.spacing.absolute32
    }

    val googleLink: NamedRuleSet by css {
        marginRight = StyleValues.spacing.absolute32
    }

    val googleLogo: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        color = Theme.palette.action4(it)
    }

    val externalLink: NamedRuleSet by css {
        display = Display.inline
        nthChild("n+2") {
            marginLeft = StyleValues.spacing.absolute16
        }
    }

}



// Private - data.

private class FooterData(footerEndpoints: FooterEndpoints) {

    val externalContentItems: Array<ExternalLinkItem> = arrayOf(
        ExternalLinkItem("md.github",  footerEndpoints.github),
        ExternalLinkItem("md.twitter", footerEndpoints.twitter),
        ExternalLinkItem("md.youtube", footerEndpoints.youtube),
        ExternalLinkItem("md.blogRss", footerEndpoints.rssFeed),
        ExternalLinkItem("md.subscribeForUpdates", footerEndpoints.newsletterSubscription),
    )

    val externalLinkItems: Array<ExternalLinkItem> = arrayOf(
        ExternalLinkItem("md.privacyPolicy",  footerEndpoints.privacyPolicy),
        ExternalLinkItem("md.termsOfService", footerEndpoints.termsOfService),
        ExternalLinkItem("md.feedback",       footerEndpoints.feedback),
    )

}
