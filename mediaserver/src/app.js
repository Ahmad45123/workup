const Koa = require("koa");
const mount = require("koa-mount");
const compress = require("koa-compress");
const body = require("koa-body");
const { SUPPORTED_MIME_TYPES } = require("./enums");

/**
 * Constructs an array of all supported MIME types.
 */
const mimeTypes = []
  .concat(SUPPORTED_MIME_TYPES.AUDIO)
  .concat(SUPPORTED_MIME_TYPES.VIDEO)
  .concat(SUPPORTED_MIME_TYPES.IMAGE);

/**
 * Instantiate a new KOA app.
 */
const app = new Koa();

/**
 * Import all the routes.
 */
const static = require("./routes/static");
const describe = require("./routes/describe");
const upload = require("./routes/upload");

/**
 * For each async piece of middleware, try it, and report on any errors.
 *
 * Koa middleware cascades, so putting this at the top of the file ensures all errors are captured.
 */
app.use(async (ctx, next) => {
  try {
    await next();
  } catch (err) {
    ctx.status = err.status || 500;
    ctx.body = {
      success: false,
      message: err.message
    };
    ctx.app.emit("error", err, ctx);
  }
});

/**
 * Compress all compressible responses over 2KB.
 * This is includes compression of all mime types listed in the config file.
 */
app.use(
  compress({
    filter: type => mimeTypes.find(mimeType => mimeType === type),
    threshold: 2048,
    flush: require("zlib").Z_SYNC_FLUSH
  })
);

/**
 * Allow KOA to parse the body of a request, including support for multipart forms.
 * Limit the maximum file size to 300MB.
 */
app.use(
  body({
    multipart: true,
    formidable: {
      maxFileSize: 300 * 1024 * 1024
    }
  })
);

/**
 * Define routes
 * - Music uses a cache-able router.
 * - Static is, well, static.
 * - Describe tells us about the available media.
 * - Upload is a secure media upload endpoint.
 */
app.use(mount("/static", static.router.routes()));
app.use(mount("/describe", describe.router.routes()));
app.use(mount("/upload", upload.router.routes()));


module.exports = app;
