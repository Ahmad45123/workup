/**
 * describe.js - Provides routes which describe the resource endpoints.
 */

const koaRouter = require("koa-router");
const fs = require("fs-extra");
const { STATIC } = require("../config");

const router = new koaRouter();

const groups = STATIC.map(group => group.NAME);

/**
 * Return all the static resource groups defined by the config file.
 *
 * Describes which groups are present, and at what path.
 */
router.get("/", async ctx => {
  ctx.body = {
    success: true,
    path: "/static/",
    groups: groups
  };
});

/**
 * For a specific group, return the files within it.
 */
router.get("/:group", async ctx => {
  /**
   * Get and escape the group from the request.
   */
  const group = ctx.params.group.toLowerCase();

  const index = groups.indexOf(group);

  if (index !== -1) {
    const filenames = await fs.readdir("./media/static/" + group);

    ctx.body = {
      success: true,
      path: "/static/" + group + "/",
      mimeType: STATIC[index].MIME_TYPE,
      files: filenames
    };
  } else {
    ctx.body = {
      success: false,
      path: null,
      fileExtension: null,
      files: null
    };
  }
});

module.exports = {
  router: router
};
