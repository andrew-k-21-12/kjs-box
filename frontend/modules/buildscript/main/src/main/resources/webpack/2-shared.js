{

    // All configs inside of this file will be applied both in the development and the production modes.
    const path         = require("path"),
          DefinePlugin = require("webpack").DefinePlugin;

    // This config is super important to prepend a slash to the compiled JS sources file:
    // without it deeper navigation routes will fail to find the sources.
    config.output.publicPath = "/";

    const assetFilenameGenerator = {
        // Instead of a function, this `filename` can be a template string.
        // Also, it's possible to set `config.output.assetModuleFilename` to configure filenames for all assets.
        filename: asset => {
            const parsedFileName = path.parse(asset.filename);
            return path.join(
                // Such replace is safe as it's not global:
                // only the first occurrence is going to be replaced,
                // so the root folder gets correctly renamed while keeping all sub-paths untouched.
                parsedFileName.dir.replace(new RegExp(RAW_OUTPUT_DIR), DESTINATION_OUTPUT_DIR),
                `${parsedFileName.name}.${asset.contentHash}${parsedFileName.ext}`
            );
        }
    };

    // Processing images.
    config.module.rules.push(
        // Inlining SVG images directly into HTML and minifying them:
        // using the same icon in multiple places
        // won't create any additional JS describing its contents at each particular call place.
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
            generator: assetFilenameGenerator
        },
        // Copying all fonts actually used in the project.
        {
            test: /\.(woff|woff2)$/i,
            type: "asset/resource",
            generator: assetFilenameGenerator
        }
    );

    // Pushing the build mode identifier to be available globally in JS sources.
    config.plugins.push(new DefinePlugin({
        BUILD_MODE: JSON.stringify(config.mode)
    }));

    // All route paths should fallback to the index page to make SPA's routes processed correctly.
    config.devServer = config.devServer || {};
    config.devServer.historyApiFallback = true;

}