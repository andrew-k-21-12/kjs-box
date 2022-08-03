package io.github.andrewk2112.resources

import io.github.andrewk2112.jsmodules.svg.SvgFile

// Icons like this are included from the compilation root and their paths are totally unrelated to browser locations,
// as a result we should use simple relative paths here.
// It's a webpack requirement for requests that should resolve in the current directory to start with "./".
// Also, each of these icons is nicely minified: using the same icon in multiple places
// won't create any additional JS describing its contents at each particular call place.
@JsModule("./icons/arrow-right-thin.svg")
@JsNonModule
external val iconArrowRightThin: SvgFile

@JsModule("./icons/material-design-logo.svg")
@JsNonModule
external val iconMaterialDesignLogo: SvgFile

@JsModule("./icons/magnify.svg")
@JsNonModule
external val iconMagnify: SvgFile
