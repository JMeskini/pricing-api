# Deploy Swagger UI to Netlify

This folder is a static Swagger UI site powered by `openapi.json`.

## Deploy steps

1. Ensure `docs/swagger/openapi.json` is up to date.
2. Create a new Netlify site.
3. Set the publish directory to `docs/swagger`.
4. Deploy.

## Update the spec

If the API changes, update `docs/swagger/openapi.json` and redeploy the site.
