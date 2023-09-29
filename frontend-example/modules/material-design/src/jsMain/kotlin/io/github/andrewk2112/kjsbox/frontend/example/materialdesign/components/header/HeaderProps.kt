package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header

import react.Props

external interface HeaderProps : Props {
    var hasCloseableMenu: Boolean
    var onMenuToggle: () -> Unit
}
