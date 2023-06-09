package io.github.andrewk2112.kjsbox.examples.frontend.md.components.common.images

import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.images.Image
import react.PropsWithClassName

external interface StrokedImageProps : PropsWithClassName {
    var image: Image
    var alternativeText: String
}
