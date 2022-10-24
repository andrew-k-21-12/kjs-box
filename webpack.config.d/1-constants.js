// Reusable constants to be used in all build modes.
const RAW_OUTPUT_DIR           = "kotlin";
const RAW_TEMPLATE_PATH        = `${RAW_OUTPUT_DIR}/index.html`;
const ASSET_FILENAME_GENERATOR = {
    filename: asset => asset.filename.replace(`${RAW_OUTPUT_DIR}/`, "")
}
