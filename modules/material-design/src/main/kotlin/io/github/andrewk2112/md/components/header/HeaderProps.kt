package io.github.andrewk2112.md.components.header

import react.Props

external interface HeaderProps : Props {
    var hasSlidingMenu: Boolean
    var onMenuClick: () -> Unit
}
