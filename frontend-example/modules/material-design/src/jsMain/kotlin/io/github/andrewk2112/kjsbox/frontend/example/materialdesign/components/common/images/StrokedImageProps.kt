package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.images

import io.github.andrewk2112.kjsbox.frontend.core.resources.Image
import react.PropsWithClassName

external interface StrokedImageProps : PropsWithClassName {
    var image: Image
    var alternativeText: String
}
