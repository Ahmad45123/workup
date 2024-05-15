const koaRouter = require("koa-router");
const basicAuth = require("koa-basic-auth");
const jimp = require("jimp");
const { STATIC, UPLOAD } = require("../config");
const { SUPPORTED_MIME_TYPES } = require("../enums");
const fs = require("fs");


const router = new koaRouter();

/**
 * Authorise this route using basic HTTP authorisation.
 * Credentials are defined in the config file.
 */
router.use(basicAuth({ name: UPLOAD.USER, pass: UPLOAD.PASSWORD }));

/**
 * Handles the POSTing of a new resource to the media server.
 */
router.post("/:group", async ctx => {
  const group = ctx.params.group.toLowerCase();

  /**
   * Return the target group definition if the group exists within the configuration file's static definition.
   */
  const targetGroup = STATIC.find(targetGroup => targetGroup.NAME === group);

  /**
   * Assert that the requested target upload resource group is a valid resource group.
   */
  ctx.assert(targetGroup, 400, "invalid resource group: " + group);

  const {resource} = ctx.request.files;

  ctx.assert(resource, 400, "must include 'resource' in POST body");

  const {filename} = ctx.request.body;

  ctx.assert(filename, 400, "must include 'filename' in POST body");

  /**
   * Deconstruct targetGroup object into individual keys for improved readability.
   */
  const { NAME, FILE_EXTENSION, MIME_TYPE } = targetGroup;

  const targetPath =
    "/static/" + NAME + "/" + escapeFilename(filename) + "." + FILE_EXTENSION;

  ctx.assert(
    extractMIMEClass(resource.type) === extractMIMEClass(MIME_TYPE),
    400,
    "cannot upload MIME class: " +
      extractMIMEClass(resource.type) +
      ", to resource group of class: " +
      extractMIMEClass(MIME_TYPE)
  );

  await save(resource, targetPath, targetGroup);

  ctx.body = { success: true, path: targetPath };
});

/**
 * Escapes a filename string:
 * - removes leading and trailing whitespace.
 * - All chars converted to lower case.
 * - Replace all spaces with '_'.
 *
 * @param {string} filename - The string filename to be escaped.
 */
const escapeFilename = filename =>
  filename
    .trim()
    .toLowerCase()
    .replace(/ /g, "_");

/**
 * Returns the high level 'class' of the MIME type from a MIME type string.
 *
 * @param {string} mimeType - The MIME type from which to extract the MIME class.
 */
const extractMIMEClass = mimeType => mimeType.split("/")[0];

/**
 * An async function which tries to save a given resource at a given path based on given parameters.
 *
 * Will handle conversion and compression of all resources.
 *
 * @param {*} resource - the resource object of a new resource to be saved.
 * @param {*} targetPath - the target local path at which the resource should be saved.
 * @param {*} targetGroup - the parameters of the target group.
 */
const save = async (resource, targetPath, { QUALITY, PIXEL_DIMENSIONS }) => {
  return new Promise(async (resolve, reject) => {
    /**
     * If the resource type is an image, apply image processing with jimp.
     * Else, if the resource is a video, reject the promise.
     * Else, if the resource is a document, pipe the file to the target path.
     * Else, the resource has an unsupported MIME type.
     */
    if (SUPPORTED_MIME_TYPES.IMAGE.find(type => type === resource.type)) {
      /**
       * Read the resource into the jimp buffer.
       */
      const image = await jimp.read(resource.path);

      /**
       * Executing methods on the returned jimp object carries out image manipulation.
       * Here we:
       * - Resize the image to the dimensions defined by the target group parameters.
       * - Compress the image based on the target group parameters.
       * - Write the file to the target path.
       */
      image
        .cover(PIXEL_DIMENSIONS.WIDTH, PIXEL_DIMENSIONS.HEIGHT)
        .quality(QUALITY)
        .write("./media" + targetPath);

      /**
       * Resolve the promise.
       */
      resolve();
    } else if (
      SUPPORTED_MIME_TYPES.VIDEO.find(type => type === resource.type)
    ) {
      reject(new Error("video processing not supported"));
    } else if (SUPPORTED_MIME_TYPES.DOC.find(type => type === resource.type)) {

      const src = fs.createReadStream(resource.path)
      const dest = fs.createWriteStream("./media" + targetPath)

      src.pipe(dest)
      src.on("close", () => {
        resolve()
      })
      src.on("error", (err) => {
        reject(new Error(err.message))
      })
    }
    else {
      reject(new Error("unsupported MIME type: " + resource.type));
    }
  }
  )
}

module.exports = {
  router: router
};
