package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons

import react.PropsWithClassName

external interface RectButtonProps : PropsWithClassName {
    var text: String
    var action: () -> Unit
    var isDark: Boolean
}
