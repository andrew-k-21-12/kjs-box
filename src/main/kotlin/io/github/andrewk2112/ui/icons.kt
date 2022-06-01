package io.github.andrewk2112.ui

import io.github.andrewk2112.jsmodules.svg.SvgFile

// Icons like this are included from the compilation root and their paths are totally unrelated to browser locations,
// as a result we should use simple relative paths here.
// It's a webpack requirement for requests that should resolve in the current directory to start with "./".
@JsModule("./icons/arrow-right-thin.svg")
@JsNonModule
external val arrowRightThin: SvgFile

@JsModule("./icons/material-design-logo.svg")
@JsNonModule
external val materialDesignLogo: SvgFile

@JsModule("./icons/magnify.svg")
@JsNonModule
external val iconMagnify: SvgFile
