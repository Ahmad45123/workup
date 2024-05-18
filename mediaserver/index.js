/**
 * Load env variables.
 */
require("dotenv").config();

/**
 * Load the server config constants.
 */
const config = require("./src/config");

/**
 * Initiate the app.
 */
const app = require("./src/app");

/**
 * Define port, and listen...
 */
const port = config.PORT;
app.listen(port, () => console.log("listening on port " + port));
