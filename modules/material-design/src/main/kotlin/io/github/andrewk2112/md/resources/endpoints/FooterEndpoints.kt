package io.github.andrewk2112.md.resources.endpoints

import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints.root

/**
 * Endpoints used in the footer leading to various external pages.
 * */
class FooterEndpoints {
    val github                 = "https://www.github.com/material-components"
    val twitter                = "https://www.twitter.com/materialdesign"
    val youtube                = "https://www.youtube.com/materialdesign"
    val rssFeed                = "$root/feed.xml"
    val newsletterSubscription = "https://services.google.com/fb/forms/materialdesignnewsletter"
    val google                 = "https://www.google.com"
    val privacyPolicy          = "https://policies.google.com/privacy"
    val termsOfService         = "https://policies.google.com/terms"
    val feedback               = root
}
