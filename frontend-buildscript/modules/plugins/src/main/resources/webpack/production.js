// All configs inside of this file will be enabled only in the production mode.
// The result webpack configurations file will be generated inside ../build/js/packages/<project-name>
// To check the outputs of this config, see ../build/distributions
if (config.mode == "production") {

    const TerserWebpackPlugin          = require("terser-webpack-plugin"),
          ImageMinimizerWebpackPlugin  = require("image-minimizer-webpack-plugin"),
          { removeUnusedTranslations } = require('i18n-unused');

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
    // Using hashes for bundles' caching.
    // The index.html will be updated correspondingly to refer the compiled JS sources.
    config.output.filename = "js/[name].[contenthash].js";

    // Removing everything from the distributions folder -
    // it also helps to prevent unnecessary files from copying: everything should be configured manually.
    config.output.clean = true;

    // Making sure the optimization and minimizer configs exist, or accessing its properties can crash otherwise.
    config.optimization = config.optimization || {};
    const minimizer = config.optimization.minimizer = config.optimization.minimizer || [];

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
                unsafe_arrows: !devServer.open, // significant improvement but makes the browser run fail
                unsafe_methods: true            // minor improvement
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

    // Removing unused translation keys before any minification or other steps.
    config.plugins.push(new RemoveUnusedTranslationsWebpackPlugin());

}
