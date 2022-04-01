// All route paths should fallback to the index page to make SPA's routes processed correctly.
const devServer = config.devServer = config.devServer || {};
devServer.historyApiFallback = true;
