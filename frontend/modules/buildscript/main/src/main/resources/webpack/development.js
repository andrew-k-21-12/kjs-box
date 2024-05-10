// All configs inside of this file will be enabled only in the development mode.
// To check the outputs of this config, see ../build/processedResources/js/main
if (config.mode === "development") {

    const HtmlWebpackPlugin = require("html-webpack-plugin");

    // Pointing to the template to be used as a base and injecting the JS sources path into it.
    config.plugins.push(new HtmlWebpackPlugin({ template: RAW_TEMPLATE_PATH }));

}
