// This config is super important to prepend a slash to the compiled JS sources file:
// without it deeper navigation routes will fail to find the sources.
config.output.publicPath = "/";

// To inline SVG images directly into HTML and minify them.
config.module.rules.push({
    test: /\.svg$/,
    use: ["@svgr/webpack"]
});

// All route paths should fallback to the index page to make SPA's routes processed correctly.
const devServer = config.devServer = config.devServer || {};
devServer.historyApiFallback = true;
