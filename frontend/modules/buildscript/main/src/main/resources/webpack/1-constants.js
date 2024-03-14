// Reusable constants for all build modes.
const DESTINATION_OUTPUT_DIR   = "static" + "/" + require("./package.json").version;
const RAW_OUTPUT_DIR           = "kotlin";
const RAW_TEMPLATE_PATH        = `${RAW_OUTPUT_DIR}/index.html`;
const ASSET_FILENAME_GENERATOR = {
    // Such replace is safe as it's not global:
    // only the first occurrence is going to be replaced,
    // so the root folder gets correctly renamed while keeping all sub-paths untouched.
    // Instead of a function, this `filename` can be a template string.
    // Also, it's possible to set `config.output.assetModuleFilename` to configure filenames for all assets.
    filename: asset => asset.filename.replace(new RegExp(RAW_OUTPUT_DIR), DESTINATION_OUTPUT_DIR)
};
