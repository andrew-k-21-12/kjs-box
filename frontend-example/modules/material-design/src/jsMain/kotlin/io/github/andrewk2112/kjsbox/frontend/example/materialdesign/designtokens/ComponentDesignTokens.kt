package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens

import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.MaterialDesignComponentImageStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.MaterialDesignComponentLabelStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.MaterialDesignComponentLayoutStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.MaterialDesignComponentSelectionStyles

/**
 * Groups all types of component design tokens together.
 */
class ComponentDesignTokens(
    val image:     MaterialDesignComponentImageStyles,
    val label:     MaterialDesignComponentLabelStyles,
    val layout:    MaterialDesignComponentLayoutStyles,
    val selection: MaterialDesignComponentSelectionStyles,
)
