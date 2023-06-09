package io.github.andrewk2112.kjsbox.examples.frontend.md.components.header

import react.Props

external interface HeaderProps : Props {
    var hasCloseableMenu: Boolean
    var onMenuToggle: () -> Unit
}
