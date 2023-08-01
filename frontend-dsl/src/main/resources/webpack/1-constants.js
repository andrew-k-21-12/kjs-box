// Reusable constants for all build modes.
const RAW_OUTPUT_DIR           = "kotlin";
const RAW_TEMPLATE_PATH        = `${RAW_OUTPUT_DIR}/index.html`;
const ASSET_FILENAME_GENERATOR = {
    // In different operating systems both types of slashes can appear, so we need to configure clean-ups for the each.
    filename: asset => asset.filename.replace(new RegExp(`${RAW_OUTPUT_DIR}(\\/|\\\\)`), "")
};
