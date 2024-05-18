const koaRouter = require("koa-router");
const fs = require("fs-extra");
const { STATIC } = require("../config");

const router = new koaRouter();

/**
 * Ensure that the static media directory exists locally on the server.
 */
fs.ensureDir("./media/static");

/**
 * For each static resource group, define a route which serves that resource group. Each route is constructed
 * by reading the configuration for each static resource group from the config file. The route will return
 * either a meaningful resource if it is found on disk, or else a default 'placeholder' image.
 */
STATIC.forEach(async group => {
  const route = "/" + group.NAME + "/:filename." + group.FILE_EXTENSION;

  /**
   * Ensure that the directory for this group exists locally on the server.
   */
  await fs.ensureDir("./media/static/" + group.NAME);

  router.get(route, async ctx => {
    ctx.set("Content-Type", group.MIME_TYPE);

    filename = ctx.params.filename.toLowerCase();

    const path =
      "./media/static/" +
      group.NAME +
      "/" +
      filename +
      "." +
      group.FILE_EXTENSION;

    const exists = await fs.pathExists(path);

    /**
     * If exists, return the file.
     * Else, return default of a given file type.
     */
    if (exists) {
      ctx.body = await fs.readFile(path);
    } else {
      ctx.body = await fs.readFile(
       "./media/defaults/" + group.DEFAULT_RESOURCE
              );
    }
  });
});

module.exports = {
  router: router
};
