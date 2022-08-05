// All configs inside of this file will be enabled only in the production mode.
// The result webpack configurations file will be generated inside ../build/js/packages/<project-name>
// To check the outputs of this config, see ../build/distributions
if (config.mode == "production") {

    const HtmlWebpackPlugin           = require("html-webpack-plugin"),
          UglifyJsWebpackPlugin       = require("uglifyjs-webpack-plugin"),
          TerserWebpackPlugin         = require("terser-webpack-plugin"),
          ImageMinimizerWebpackPlugin = require("image-minimizer-webpack-plugin"),
          CopyWebpackPlugin           = require("copy-webpack-plugin"),
          NodeJsonMinify              = require("node-json-minify");

    // Where to output and how to name JS sources.
    // Using hashes for bundles' caching.
    // The index.html will be updated correspondingly to refer the compiled JS sources.
    config.output.filename = "js/[name].[contenthash].js";

    // Removing everything from the distributions folder -
    // it also helps to prevent unnecessary files from copying: everything should be configured manually.
    config.output.clean = true;

    // Making sure optimization and minimizer configs exist, or accessing its properties can crash otherwise.
    config.optimization = config.optimization || {};
    const minimizer = config.optimization.minimizer = config.optimization.minimizer || [];

    // Minifying HTML.
    minimizer.push(new HtmlWebpackPlugin({
        template: "kotlin/index.html",
        minify: {
            removeAttributeQuotes: true,
            collapseWhitespace: true,
            removeComments: true,
        },
    }));

    // Minifying and obfuscating JS.
    minimizer.push(new UglifyJsWebpackPlugin({
        parallel: true,   // speeds up the compilation
        sourceMap: false, // help to match obfuscated functions with their origins, not needed for now
        uglifyOptions: {
            compress: {
                drop_console: true, // removing console calls
            }
        }
    }));

    // Additional JS minification, removing all comments entirely.
    minimizer.push(new TerserWebpackPlugin({
        terserOptions: {
            compress: {
                passes: 5,        // significant improvement
                hoist_funs: true, // minor improvement
                unsafe: true,     // minor improvement
                drop_console: true,
                ecma: 2016, // all configs below require it
                unsafe_arrows: !devServer.open, // significant improvement but makes the browser run fail
                unsafe_methods: true,           // minor improvement
                module: true                    // minor improvement
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
                    ],
                },
            },
        ],
        minimizer: {
            implementation: ImageMinimizerWebpackPlugin.imageminMinify,
            options: {
                plugins: [
                    ["optipng", { optimizationLevel: 7 }], // lossless PNG optimization
                ],
            },
        },
    }))

    // Minifying and copying JSON locales, copying fonts into the bundle.
    config.plugins.push(new CopyWebpackPlugin({
        patterns: [
            {
                context: "kotlin",
                from: "locales/**/*.json",
                to: "[path][name][ext]",
                transform: content => NodeJsonMinify(content.toString())
            },
            {
                context: "kotlin",
                from: "fonts/**/*.woff2",
                to: "[path][name][ext]"
            }
        ]
    }));

}
