package io.github.andrewk2112.md.components.header

import react.Props

external interface HeaderProps : Props {
    var hasCloseableMenu: Boolean
    var onMenuToggle: () -> Unit
}
