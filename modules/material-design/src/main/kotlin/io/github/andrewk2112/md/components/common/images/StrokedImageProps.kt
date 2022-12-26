package io.github.andrewk2112.md.components.common.images

import io.github.andrewk2112.resources.images.Image
import react.PropsWithClassName

external interface StrokedImageProps : PropsWithClassName {
    var image: Image
    var alternativeText: String
}
