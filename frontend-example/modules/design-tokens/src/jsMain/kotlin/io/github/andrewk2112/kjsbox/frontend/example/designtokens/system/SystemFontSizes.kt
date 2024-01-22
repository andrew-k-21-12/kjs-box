package io.github.andrewk2112.kjsbox.frontend.example.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.ContextedFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.ReferenceFontSizes

class SystemFontSizes(private val fontSizes: ReferenceFontSizes) : ContextedFontSizes<Context>() {

    val adaptive = get { if (screenSize > Context.ScreenSize.PHONE) fontSizes.relative1p5 else fontSizes.relative2 }

}
