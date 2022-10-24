// All configs inside of this file will be applied both in the development and the production modes.
const path              = require("path"),
      DefinePlugin      = require("webpack").DefinePlugin,
      HtmlWebpackPlugin = require("html-webpack-plugin");

// Setting the entry point module.
config.entry = path.resolve(__dirname, `${RAW_OUTPUT_DIR}/${config.output.library}-index.js`);

// This config is super important to prepend a slash to the compiled JS sources file:
// without it deeper navigation routes will fail to find the sources.
config.output.publicPath = "/";

// Processing images.
config.module.rules.push(
    // Inlining SVG images directly into HTML and minifying them.
    {
        test: /\.svg$/,
        use: ["@svgr/webpack"]
    },
    // Treating files with the following extensions as raster assets.
    {
        test: /\.(jpe?g|png|gif)$/i,
        type: "asset",
        parser: {
            dataUrlCondition: {
                maxSize: 0 * 1024 // all images of less than this size (in bytes) are going to be inlined
            }
        },
        generator: ASSET_FILENAME_GENERATOR
    },
    // Copying all fonts actually used in the project.
    {
        test: /\.(woff|woff2)$/i,
        type: "asset/resource",
        generator: ASSET_FILENAME_GENERATOR
    }
);

// Pushing the project name and the build mode identifier to be available globally in JS sources.
config.plugins.push(new DefinePlugin({
    PROJECT_NAME: JSON.stringify(config.output.library),
    BUILD_MODE:   JSON.stringify(config.mode)
}));

// All route paths should fallback to the index page to make SPA's routes processed correctly.
const devServer = config.devServer = config.devServer || {};
devServer.historyApiFallback = true;
