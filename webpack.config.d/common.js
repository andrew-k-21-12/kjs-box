const webpack = require("webpack");

// This config is super important to prepend a slash to the compiled JS sources file:
// without it deeper navigation routes will fail to find the sources.
config.output.publicPath = "/";

// To inline SVG images directly into HTML and minify them.
config.module.rules.push({
    test: /\.svg$/,
    use: ["@svgr/webpack"]
});

// Pushing the build mode identifier to be available globally in JS sources.
config.plugins.push(new webpack.DefinePlugin({
    BUILD_MODE: JSON.stringify(config.mode)
}));

// All route paths should fallback to the index page to make SPA's routes processed correctly.
const devServer = config.devServer = config.devServer || {};
devServer.historyApiFallback = true;
