// All configs inside of this file will be enabled only in the production mode.
// The result webpack configurations file will be generated inside ../build/js/packages/<project-name>
// To check the outputs of this config, see ../build/dist/js/productionExecutable
if (config.mode === "production") {

    const fs                           = require("fs"),
          path                         = require("path"),
          HtmlWebpackPlugin            = require("html-webpack-plugin"),
          TerserWebpackPlugin          = require("terser-webpack-plugin"),
          ImageMinimizerWebpackPlugin  = require("image-minimizer-webpack-plugin"),
          CopyWebpackPlugin            = require("copy-webpack-plugin"),
          { removeUnusedTranslations } = require("i18n-unused");

    // Removes unused translation keys.
    // If i18n-unused becomes deprecated or stops to work stable,
    // it's possible to create a similar tool which parses source files and drops translation keys unmet in the code
    // or modify translation keys to be less ambiguous.
    class RemoveUnusedTranslationsWebpackPlugin {
        apply(compiler) {
            // The environment hook seems to be optimal to trigger this operation right after Kotlin has been compiled,
            // but before any webpack processing, see https://webpack.js.org/api/compiler-hooks/#environment.
            compiler.hooks.environment.tap("RemoveUnusedTranslationsWebpackPlugin", () => {
                removeUnusedTranslations({
                    localesPath: `${RAW_OUTPUT_DIR}/locales`, // raw locales
                    srcPath:      RAW_OUTPUT_DIR,             // and JS files prepared after the Kotlin compilation step
                    translationKeyMatcher: /(?:.+ = ')(.+)(?:';)/gi // looks for patterns like: key = 'keyValue';
                })
                .then(result => {
                    if (result.totalCount > 0) {
                        console.warn("Unused translations were removed:\n" + JSON.stringify(result));
                    }
                });
            });
        }
    }

    // Disabling source maps to have the maximal build speed and avoid distribution of unnecessary map files.
    // Source maps can be enabled to help with debugging to match obfuscated functions with their original versions.
    config.devtool = false;

    // Where to output and how to name JS sources.
    // Using hashes for bundles' caching and optimization of recompilations.
    // The index.html will be updated correspondingly to refer the compiled JS sources.
    config.output.filename      = `${DESTINATION_OUTPUT_DIR}/js/[name].[contenthash].js`;
    config.output.chunkFilename = `${DESTINATION_OUTPUT_DIR}/js/[name].[contenthash].js`; // JS chunks for lazy modules
                                                                                          // and other parts

    // Removing everything from the distributions folder -
    // it also helps to prevent unnecessary files from copying: everything should be configured manually.
    config.output.clean = true;

    // Making sure various optimization config variables are defined to avoid crashes on attempts to access them.
    config.optimization = config.optimization || {};
    config.optimization.splitChunks = config.optimization.splitChunks || {};
    const cacheGroups = config.optimization.splitChunks.cacheGroups = config.optimization.splitChunks.cacheGroups || {};
    const minimizer = config.optimization.minimizer = config.optimization.minimizer || [];

    // All Kotlin modules are going to be packed into separate JS chunks
    // according to webpack's default configs (for example, these chunks can not be too small).
    // There is an issue with the coroutines chunk having different content hashes even if its version wasn't changed -
    // it can be bypassed by performing the compilation for multiple times (until the right chunk is obtained).
    fs.readdirSync(RAW_OUTPUT_DIR).forEach(fileName => {
        const parsedFileName = path.parse(fileName);
        if (parsedFileName.ext === ".js") {
            const chunkName = parsedFileName.name;
            cacheGroups[chunkName] = {
                test:   module => module.resource && module.resource.endsWith(parsedFileName.base),
                name:   chunkName,
                chunks: "all", // forces the extraction, even if its parts could be loaded later with other chunks
            };
        }
    });

    // Gathering all JS React dependencies into a single chunk.
    cacheGroups.jsReact = {
        // When files paths are processed by webpack, they always contain / on Unix systems and \ on Windows.
        // That's why using [\\/] is necessary to represent a path separator.
        // / or \ will cause issues when used cross-platform.
        test:     /[\\/]node_modules[\\/](react|react-dom|react-router|react-router-dom)[\\/]/,
        priority: 2,
        name:     "js-react",
        chunks:   "all",
    };

    // Gathering all the rest JS dependencies into a single chunk.
    cacheGroups.jsAll = {
        test:     /[\\/]node_modules[\\/]/,
        priority: 1,
        name:     "js-all",
        chunks:   "all",
    };

    // Minifying HTML.
    minimizer.push(new HtmlWebpackPlugin({
        template: RAW_TEMPLATE_PATH,
        minify: {
            removeAttributeQuotes: true,
            collapseWhitespace: true,
            removeComments: true
        }
    }));

    // Minifying and obfuscating JS, removing all comments entirely.
    // Mangling for properties can help to reduce the bundle size a lot, but the distribution doesn't launch with it.
    minimizer.push(new TerserWebpackPlugin({
        terserOptions: {
            ecma: 2015, // these two options are applied to all nested configs here, provide minor improvement
            module: true,
            compress: {
                drop_console: true, // removing console calls
                passes: 5,          // significant improvement
                hoist_funs: true,   // all configs including this and before the next comment provide minor improvements
                hoist_vars: true,
                unsafe: true,
                // All configs below require modern ECMA enabled.
                unsafe_arrows: !config.devServer.open, // significant improvement but makes the browser run fail
                unsafe_methods: true                   // minor improvement
            },
            format: {
                comments: false
            }
        },
        extractComments: false
    }));

    // Generation and minification for images.
    minimizer.push(new ImageMinimizerWebpackPlugin({
        generator: [
            {
                preset: "webp",
                implementation: ImageMinimizerWebpackPlugin.imageminGenerate,
                options: {
                    plugins: [
                        ["webp", { quality: 100 }]
                    ]
                }
            }
        ],
        minimizer: {
            implementation: ImageMinimizerWebpackPlugin.imageminMinify,
            options: {
                plugins: [
                    ["optipng", { optimizationLevel: 7 }] // lossless PNG optimization
                ]
            }
        }
    }));

    // There is no way for webpack to understand that the service worker's sources are a part of the bundle,
    // so configuring its copying manually.
    // Updating the destination name as well to avoid overwriting of the file during bundling.
    config.plugins.push(new CopyWebpackPlugin({
        patterns: [
            { from: `./${RAW_OUTPUT_DIR}/service-worker-source.js`, to: "service-worker.js" }
        ],
    }));

    // Removing unused translation keys before any minification or other steps.
    config.plugins.push(new RemoveUnusedTranslationsWebpackPlugin());

}
