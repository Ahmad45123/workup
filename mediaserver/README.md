# `media-server`

A media server that serves static resources.

## ENV 

- `UPLOAD_USER`
- `UPLOAD_PASSWORD`
- `DISCOGS_API_KEY`


## Static Endpoints

For all static resources, this server will attempt to return a relevant resource, or else if the resource does not exist, it will return a default 'placeholder' resource. This prevents clients from having no resource to display at all; clients can make use of this media server's 'describe' endpoint to learn about what resources are available.

### `GET` /static/icons/:icon.png

Returns an icon based on the filename.

`:icon` can be any icon filename.

Example: return an icon with filename 'accept.png'.

```bash
curl --request GET \
  --url http://path_to_server/static/icons/accept.png
```

### `GET` /static/resume/:resume.pdf
 
Returns a resume based on the filename.

`:resume` can be any resume filename.

Example: return a resume with filename 'resume.pdf'.

```bash
curl --request GET \
  --url http://path_to_server/static/resume/resume.pdf
```


## Describe

### `GET` /describe

Returns a JSON representation of the media groups.

Example: return JSON containing of all groups present.

```bash
curl --request GET \
  --url http://localhost:8910/describe
```

```json
{
  "success": true,
  "path": "/static/",
  "groups": ["icons", "resume"]
}
```

### `GET` /describe/:group

Returns a JSON representation of all the files current present for a given group.

`:group` can be any valid group.

Example: return JSON containing all the media resources for a the exec resource group.

```bash
curl --request GET \
  --url http://localhost:8910/describe/exec
```

```json
{
  "success": true,
  "path": "/static/exec/",
  "mimeType": "image/jpeg",
  "files": []
}
```

## Upload

Upload and convert media to any of the given static resource group.

All upload routes are protected by basic HTTP auth. The credentials are defined by ENV variables `UPLOAD_USER` and `UPLOAD_PASSWORD`.

### `POST` /upload/:group

POST a resource to a given group, assigning that resource a given filename.

`:group` can be any valid group.

```bash
curl --location 'http://path-to-server/upload/resume/' \
--header 'Authorization: Basic dXNlcjpwYXNz' \
--form 'resource=@"/C:/Users/ibrah/Downloads/Ibrahim_Abou_Elenein_Resume.pdf"' \
--form 'filename="aboueleyes-reume-2"'
```

```json
{
  "success": true,
  "path": "/static/resume/aboueleyes-reume-2.pdf"
}
```

A resource at `http://path_to_server/static/resume/aboueleyes-reume-2.pdf` will now be available.
